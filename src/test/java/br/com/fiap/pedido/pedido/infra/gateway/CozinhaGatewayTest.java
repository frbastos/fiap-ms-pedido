package br.com.fiap.pedido.pedido.infra.gateway;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.usecase.CozinhaGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.feignclient.ProducaoFeignClient;
import br.com.fiap.pedido.pedido.infra.gateways.CozinhaGatewayImpl;
import br.com.fiap.shared.PedidoHelper;

class CozinhaGatewayTest {

    private AutoCloseable autoCloseable;

    @Mock
    private ProducaoFeignClient producaoFeignClient;

    private CozinhaGateway cozinhaGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        cozinhaGateway = new CozinhaGatewayImpl(producaoFeignClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirRegistrarPedido() {
        Pedido pedido = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);

        cozinhaGateway.registrarPedido(pedido);

        verify(producaoFeignClient).registrarNovoPedido(argThat(request ->
                request.numeroPedido().equals(pedido.getNumeroPedido()) &&
                request.itens().size() == pedido.getItens().size()));
    }

    @Test
    void devePermitirRegistrarPedidoComClienteNulo() {
        Pedido pedido = PedidoHelper.gerar(1010L, StatusPedido.PENDENTE);

        cozinhaGateway.registrarPedido(pedido);

        verify(producaoFeignClient).registrarNovoPedido(argThat(request ->
                request.numeroPedido().equals(pedido.getNumeroPedido()) &&
                request.cliente() == null &&
                request.itens().size() == pedido.getItens().size()));
    }

}
