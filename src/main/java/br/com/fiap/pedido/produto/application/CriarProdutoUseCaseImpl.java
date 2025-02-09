package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;

public class CriarProdutoUseCaseImpl implements CriarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public CriarProdutoUseCaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public Produto criar(Produto produto) {
        return this.produtoGateway.salvar(produto);
    }

}
