package br.com.fiap.pedido.pedido.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.pedido.pedido.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorIdUseCase;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorNumeroPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.CriarPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.GerarQRCodePagamentoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ListarTodosPedidosUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ProcessarPagamentoPedidoUseCase;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.PedidoController;
import br.com.fiap.pedido.pedido.infra.api.dto.InformacoesPagamentosRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.shared.exception.ErrorHandlingControllerAdvice;
import br.com.fiap.shared.PedidoHelper;

public class PedidoControllerTest {

        private MockMvc mockMvc;

        private AutoCloseable autoCloseable;

        @Mock
        private ListarTodosPedidosUseCase listarTodosPedidosUseCase;

        @Mock
        private BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;

        @Mock
        private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

        @Mock
        private CriarPedidoUseCase criarPedidoUseCase;

        @Mock
        private BuscarPedidoPorNumeroPedidoUseCase buscarPedidoPorNumeroPedidoUseCase;

        @Mock
        private GerarQRCodePagamentoUseCase gerarQRCodePagamentoUseCase;

        @Mock
        private ProcessarPagamentoPedidoUseCase processarPagamentoPedidoUseCase;

        private PedidoController pedidoController;

        @BeforeEach
        void setUp() {
                autoCloseable = MockitoAnnotations.openMocks(this);
                pedidoController = new PedidoController(listarTodosPedidosUseCase,
                                atualizarStatusPedidoUseCase, criarPedidoUseCase, buscarPedidoPorNumeroPedidoUseCase,
                                gerarQRCodePagamentoUseCase, processarPagamentoPedidoUseCase);
                mockMvc = MockMvcBuilders.standaloneSetup(pedidoController)
                                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                                .build();
        }

        @AfterEach
        void tearDown() throws Exception {
                autoCloseable.close();
        }

        @Test
        void devePermitirCriarPedido() throws Exception {

                PedidoRequest request = PedidoHelper.gerarRequest();
                Pedido pedido = PedidoHelper.gerar(request);
                String qrcode = UUID.randomUUID().toString();

                when(criarPedidoUseCase.criar(request))
                                .thenReturn(pedido);

                when(gerarQRCodePagamentoUseCase.gerar(pedido.getId(), pedido.getPrecoTotal()))
                                .thenReturn(qrcode);

                mockMvc.perform(
                                post("/pedidos")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(asJsonString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.numeroPedido").value(pedido.getNumeroPedido()))
                                .andExpect(jsonPath("$.qrcode").value(qrcode));

                verify(criarPedidoUseCase).criar(any());
                verify(gerarQRCodePagamentoUseCase).gerar(anyLong(), any());

        }

        @Test
        void devePermitirRetornarStatusPagamentoDoPedido() throws Exception {

                Pedido pedido = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);

                when(buscarPedidoPorNumeroPedidoUseCase.buscar(pedido.getNumeroPedido()))
                                .thenReturn(Optional.of(pedido));

                mockMvc.perform(
                                get("/pedidos/statePayment/{numeroPedido}", pedido.getNumeroPedido())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.orderNumber").value(pedido.getNumeroPedido()))
                                .andExpect(jsonPath("$.statusConfirmacaoPagamento")
                                                .value(pedido.getInformacoesPagamento().statusConfirmacaoPagamento()
                                                                .toString()));

        }

        @Test
        void devePermitirRetornarFollowupDosPedidos() throws Exception {

                Pedido pedido1 = PedidoHelper.gerar(1L, StatusPedido.RECEBIDO);
                Pedido pedido2 = PedidoHelper.gerar(2L, StatusPedido.PREPARANDO);
                Pedido pedido3 = PedidoHelper.gerar(3L, StatusPedido.PRONTO);

                when(listarTodosPedidosUseCase.listarTodosNaoFinalizados())
                                .thenReturn(Arrays.asList(pedido1, pedido2, pedido3));

                mockMvc.perform(
                                get("/pedidos/follow-up")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                // .andDo(print())
                                .andExpect(jsonPath("$.readyOrders[0].orderNumber").value(pedido2.getNumeroPedido()))
                                .andExpect(jsonPath("$.preparingOrders[0].orderNumber")
                                                .value(pedido2.getNumeroPedido()))
                                .andExpect(jsonPath("$.receivedOrders[0].orderNumber")
                                                .value(pedido3.getNumeroPedido()));

                verify(listarTodosPedidosUseCase).listarTodosNaoFinalizados();

        }

        @Test
        void devePermitirProcessarPedido() throws Exception {

                Long pedidoId = 1010L;
                InformacoesPagamentosRequest request = new InformacoesPagamentosRequest(UUID.randomUUID().toString(),
                                BigDecimal.valueOf(100), StatusConfirmacaoPagamento.APROVADO);

                mockMvc.perform(
                                post("/pedidos/{pedidoId}/pagamento", pedidoId)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(asJsonString(request)))
                                .andExpect(status().isOk());

                verify(processarPagamentoPedidoUseCase).processar(any());
        }

        public static String asJsonString(final Object obj) {
                try {
                        return new ObjectMapper().writeValueAsString(obj);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

}
