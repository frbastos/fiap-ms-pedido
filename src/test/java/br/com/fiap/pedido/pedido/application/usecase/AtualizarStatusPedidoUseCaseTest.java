package br.com.fiap.pedido.pedido.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.shared.PedidoHelper;

class AtualizarStatusPedidoUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PedidoGateway gateway;

    private AtualizarStatusPedidoUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new AtualizarStatusPedidoUseCaseImpl(gateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePemitirAtualizarStatusPedido() {

        Pedido pedido = PedidoHelper.gerar();

        when(gateway.buscarPorNumeroPedido(any(Long.class)))
                .thenReturn(Optional.of(pedido));

        when(gateway.salvar(pedido))
                .thenReturn(pedido);

        Pedido pedidoAtualizado = usecase.atualizar(pedido.getNumeroPedido(), StatusPedido.PREPARANDO);

        assertNotNull(pedidoAtualizado);
        verify(gateway).buscarPorNumeroPedido(anyLong());
        verify(gateway).salvar(any(Pedido.class));

    }

}
