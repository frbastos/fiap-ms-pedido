package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.infra.exceptions.ProdutoEmUsoException;

public interface RemoverProdutoUseCase {

    void remover(Long id) throws ProdutoEmUsoException;

}
