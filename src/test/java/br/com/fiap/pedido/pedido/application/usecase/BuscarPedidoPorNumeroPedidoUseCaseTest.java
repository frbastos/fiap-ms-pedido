package br.com.fiap.pedido.pedido.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.shared.PedidoHelper;

class BuscarPedidoPorNumeroPedidoUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PedidoGateway gateway;

    private BuscarPedidoPorNumeroPedidoUseCaseImpl usecase;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new BuscarPedidoPorNumeroPedidoUseCaseImpl(gateway);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

     @Test
    void devePermitirBuscarPedidoPorNumero() {
        Pedido pedido = PedidoHelper.gerar();

        when(gateway.buscarPorNumeroPedido(anyLong())).thenReturn(Optional.of(pedido));

        Optional<Pedido> result = usecase.buscar(anyLong());

        assertTrue(result.isPresent());
        assertEquals(pedido, result.get());
    }

}
