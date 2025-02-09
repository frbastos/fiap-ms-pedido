package br.com.fiap.pedido.produto.application;

import java.util.Optional;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public class BuscarProdutoPorIdUseCaseImpl implements BuscarProdutoPorIdUseCase {

    private final ProdutoGateway productGateway;

    public BuscarProdutoPorIdUseCaseImpl(ProdutoGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) throws NaoEncontradoException {
        return this.productGateway.buscarPorId(id);
    }

}
