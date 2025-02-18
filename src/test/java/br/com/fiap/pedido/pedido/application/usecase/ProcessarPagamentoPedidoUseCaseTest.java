package br.com.fiap.pedido.pedido.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.application.usecase.command.InformacoesPagamentosCommand;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.shared.PedidoHelper;

class ProcessarPagamentoPedidoUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;

    @Mock
    private EnviarPedidoCozinhaUseCase enviarPedidoCozinhaUseCase;

    private ProcessarPagamentoPedidoUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new ProcessarPagamentoPedidoUseCaseImpl(pedidoGateway, buscarPedidoPorIdUseCase,
                enviarPedidoCozinhaUseCase);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirProcessarPedido_StatusAprovado() {
        Pedido pedido = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);
        String transacaoId = UUID.randomUUID().toString();
        InformacoesPagamentosCommand command = new InformacoesPagamentosCommand(pedido.getId(), transacaoId,
                pedido.getPrecoTotal(),
                StatusConfirmacaoPagamento.APROVADO);

        when(buscarPedidoPorIdUseCase.buscar(pedido.getId()))
                .thenReturn(Optional.of(pedido));
        when(pedidoGateway.salvar(any()))
            .thenAnswer(i -> i.getArgument(0));
        doNothing().when(enviarPedidoCozinhaUseCase).enviar(any(Pedido.class));

        usecase.processar(command);

        verify(buscarPedidoPorIdUseCase).buscar(anyLong());
        verify(pedidoGateway).salvar(any());
        verify(enviarPedidoCozinhaUseCase).enviar(any());
    }

    @Test
    void devePermitirProcessarPedido_StatusDiferenteAprovado() {
        Pedido pedido = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);
        String transacaoId = UUID.randomUUID().toString();
        InformacoesPagamentosCommand command = new InformacoesPagamentosCommand(pedido.getId(), transacaoId,
                pedido.getPrecoTotal(),
                StatusConfirmacaoPagamento.PENDENTE);

        when(buscarPedidoPorIdUseCase.buscar(pedido.getId()))
                .thenReturn(Optional.of(pedido));
        when(pedidoGateway.salvar(any()))
            .thenAnswer(i -> i.getArgument(0));
        doNothing().when(enviarPedidoCozinhaUseCase).enviar(any(Pedido.class));

        usecase.processar(command);

        verify(buscarPedidoPorIdUseCase).buscar(anyLong());
        verify(pedidoGateway).salvar(any());
        verify(enviarPedidoCozinhaUseCase, times(0)).enviar(any());
    }

}
