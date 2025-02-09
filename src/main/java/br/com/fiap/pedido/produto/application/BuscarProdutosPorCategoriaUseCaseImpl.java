package br.com.fiap.pedido.produto.application;

import java.util.List;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public class BuscarProdutosPorCategoriaUseCaseImpl implements BuscarProdutosPorCategoriaUseCase {

    private final ProdutoGateway productGateway;

    public BuscarProdutosPorCategoriaUseCaseImpl(ProdutoGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public List<Produto> buscarPorCategoria(Categoria categoria) {
        return this.productGateway.buscarPorCategoria(categoria);
    }

}
