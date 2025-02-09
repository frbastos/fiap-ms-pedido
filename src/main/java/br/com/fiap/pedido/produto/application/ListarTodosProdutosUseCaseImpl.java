package br.com.fiap.pedido.produto.application;

import java.util.List;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;

public class ListarTodosProdutosUseCaseImpl implements ListarTodosProdutosUseCase  {

    private final ProdutoGateway produtoGateway;

    public ListarTodosProdutosUseCaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public List<Produto> listarTodos() {
        return this.produtoGateway.listarTodos();
    }

}
