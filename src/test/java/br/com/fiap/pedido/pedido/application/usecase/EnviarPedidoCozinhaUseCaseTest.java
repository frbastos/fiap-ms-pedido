package br.com.fiap.pedido.pedido.application.usecase;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.shared.PedidoHelper;

class EnviarPedidoCozinhaUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private CozinhaGateway gateway;

    private EnviarPedidoCozinhaUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new EnviarPedidoCozinhaUseCaseImpl(gateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirEnviarPedidoCozinha() {

        Pedido pedido = PedidoHelper.gerar();

        doNothing().when(gateway).registrarPedido(pedido);

        usecase.enviar(pedido);

        verify(gateway).registrarPedido(pedido);

    }

}
