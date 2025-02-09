package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;
import br.com.fiap.pedido.produto.doimain.entities.Produto;

public interface AtualizarProdutoUseCase {

    Produto atualizar(AtualizaProdutoCommand update);

}
