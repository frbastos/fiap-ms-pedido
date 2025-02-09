package br.com.fiap.pedido.produto.application;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.infra.exceptions.ProdutoEmUsoException;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public class RemoverProdutoUseCaseImpl implements RemoverProdutoUseCase {

    private final ProdutoGateway productGateway;

    public RemoverProdutoUseCaseImpl(ProdutoGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void remover(Long id) throws ProdutoEmUsoException {
        Produto product = productGateway.buscarPorId(id).orElseThrow((NaoEncontradoException::new));
        productGateway.remover(product.getId());
    }

}
