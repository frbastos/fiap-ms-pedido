package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public class AtualizarProdutoUseCaseImpl implements AtualizarProdutoUseCase{

   private final ProdutoGateway produtoGateway;

    public AtualizarProdutoUseCaseImpl(ProdutoGateway produtoGateway) {
       this.produtoGateway = produtoGateway;
    }

    @Override
    public Produto atualizar(AtualizaProdutoCommand update) {
        Produto product = this.produtoGateway.buscarPorId(update.id()).orElseThrow((NaoEncontradoException::new));
        product.atualizar(update);
        return this.produtoGateway.salvar(product);
    }

}
