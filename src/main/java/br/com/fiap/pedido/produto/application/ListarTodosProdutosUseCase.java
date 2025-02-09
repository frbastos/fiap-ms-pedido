package br.com.fiap.pedido.produto.application;

import java.util.List;

import br.com.fiap.pedido.produto.doimain.entities.Produto;

public interface ListarTodosProdutosUseCase {

    List<Produto> listarTodos();

}
