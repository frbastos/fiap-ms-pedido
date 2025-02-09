package br.com.fiap.pedido.pedido.application.usecase;

import java.util.Optional;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public class BuscarPedidoPorNumeroPedidoUseCaseImpl implements BuscarPedidoPorNumeroPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public BuscarPedidoPorNumeroPedidoUseCaseImpl(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public Optional<Pedido> buscar(Long orderNumber) {
        return this.pedidoGateway.buscarPorNumeroPedido(orderNumber);
    }
}
