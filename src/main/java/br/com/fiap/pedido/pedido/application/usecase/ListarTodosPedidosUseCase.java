package br.com.fiap.pedido.pedido.application.usecase;

import java.util.List;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public interface ListarTodosPedidosUseCase {

    List<Pedido> listarTodos();

    List<Pedido> listarTodosNaoFinalizados();

}
