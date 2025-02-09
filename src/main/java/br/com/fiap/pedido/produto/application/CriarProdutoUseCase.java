package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.doimain.entities.Produto;

public interface CriarProdutoUseCase {

    Produto criar(Produto persistence);

}
