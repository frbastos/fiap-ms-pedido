package br.com.fiap.pedido.pedido.application.usecase;

import java.util.List;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public class ListarTodosPedidosUseCaseImpl implements ListarTodosPedidosUseCase {

    private final PedidoGateway orderGateway;

    public ListarTodosPedidosUseCaseImpl(PedidoGateway orderGateway){
        this.orderGateway = orderGateway;
    }

    @Override
    public List<Pedido> listarTodos() {
        return orderGateway.listarTodos();
    }

    @Override
    public List<Pedido> listarTodosNaoFinalizados() {
        return orderGateway.listarTodosNaoFinalizados();
    }


}
