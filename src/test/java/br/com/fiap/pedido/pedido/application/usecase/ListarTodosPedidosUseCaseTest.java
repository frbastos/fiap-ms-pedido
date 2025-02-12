package br.com.fiap.pedido.pedido.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.shared.PedidoHelper;

class ListarTodosPedidosUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PedidoGateway gateway;

    private ListarTodosPedidosUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new ListarTodosPedidosUseCaseImpl(gateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirListarPedidos() {

        Pedido pedido1 = PedidoHelper.gerar();
        Pedido pedido2 = PedidoHelper.gerar();

        when(gateway.listarTodos())
                .thenReturn(Arrays.asList(pedido1, pedido2));

        List<Pedido> resultado = usecase.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(gateway).listarTodos();
    }

    @Test
    void devePermitirListarPedidosNaoFinalizados() {

        Pedido pedido1 = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);
       
        when(gateway.listarTodos())
                .thenReturn(Arrays.asList(pedido1));

        List<Pedido> resultado = usecase.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(gateway).listarTodos();
    }

}
