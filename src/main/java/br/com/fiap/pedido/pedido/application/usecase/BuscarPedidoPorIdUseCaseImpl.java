package br.com.fiap.pedido.pedido.application.usecase;

import java.util.Optional;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public class BuscarPedidoPorIdUseCaseImpl implements BuscarPedidoPorIdUseCase {

   private final PedidoGateway orderGateway;

    public BuscarPedidoPorIdUseCaseImpl(PedidoGateway orderGateway){
        this.orderGateway = orderGateway;
    }

    @Override
    public Optional<Pedido> buscar(Long id) {
        return orderGateway.buscarPorId(id);
    }

}
