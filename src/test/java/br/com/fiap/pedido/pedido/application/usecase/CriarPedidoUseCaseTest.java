package br.com.fiap.pedido.pedido.application.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.dto.request.ItemPedidoRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import br.com.fiap.shared.ClienteHelper;
import br.com.fiap.shared.ProdutoHelper;

class CriarPedidoUseCaseTest {

    private AutoCloseable autoCloseable;
    private CriarPedidoUseCaseImpl usecase;

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private BuscarClienteUseCase buscarClienteUseCase;

    @Mock
    private BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new CriarPedidoUseCaseImpl(pedidoGateway, buscarClienteUseCase, buscarProdutoPorIdUseCase);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirCriarPedido() {

        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();
        Produto xSalada = ProdutoHelper.gerarXSalada();
        Produto cocaCola = ProdutoHelper.gerarXSalada();

        ItemPedidoRequest item1 = new ItemPedidoRequest(xSalada.getId(), 1, null);
        ItemPedidoRequest item2 = new ItemPedidoRequest(cocaCola.getId(), 1, null);

        PedidoRequest pedidoRequest = new PedidoRequest(cliente.getDocumento(), Arrays.asList(item1, item2));

        when(buscarClienteUseCase.buscarPorDocumento(anyString()))
                .thenReturn(Optional.of(cliente));

        when(pedidoGateway.buscarNumeroPedido())
                .thenReturn(1L);

        when(buscarProdutoPorIdUseCase.buscarPorId(xSalada.getId()))
                .thenReturn(Optional.of(xSalada));

        when(buscarProdutoPorIdUseCase.buscarPorId(cocaCola.getId()))
                .thenReturn(Optional.of(cocaCola));

        when(pedidoGateway.salvarEAtualizar(any(Pedido.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        Pedido resultado = usecase.criar(pedidoRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(cliente, resultado.getCliente());
        assertEquals(StatusPedido.PENDENTE, resultado.getStatus());
        assertEquals(1L, resultado.getNumeroPedido());
        assertEquals(2, resultado.getItens().size());

        verify(buscarClienteUseCase, times(1)).buscarPorDocumento(anyString());
        verify(buscarProdutoPorIdUseCase, times(2)).buscarPorId(anyLong());
        verify(pedidoGateway, times(1)).salvarEAtualizar(any());

    }

    @Test
    void deveGerarExcecao_QuandoProduto_NaoEncontrado() {
        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();
        Produto xSalada = ProdutoHelper.gerarXSalada();

        ItemPedidoRequest item1 = new ItemPedidoRequest(xSalada.getId(), 1, null);

        PedidoRequest pedidoRequest = new PedidoRequest(cliente.getDocumento(), Arrays.asList(item1));

        when(buscarClienteUseCase.buscarPorDocumento(anyString()))
                .thenReturn(Optional.of(cliente));

        when(pedidoGateway.buscarNumeroPedido())
                .thenReturn(1L);

        when(buscarProdutoPorIdUseCase.buscarPorId(xSalada.getId()))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> usecase.criar(pedidoRequest))
                .isInstanceOf(NaoEncontradoException.class);

        verify(buscarClienteUseCase, times(1)).buscarPorDocumento(anyString());
        verify(buscarProdutoPorIdUseCase, times(1)).buscarPorId(anyLong());

    }

}
