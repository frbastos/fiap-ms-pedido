package br.com.fiap.pedido.produto.application;

import java.util.List;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public interface BuscarProdutosPorCategoriaUseCase {

    List<Produto> buscarPorCategoria(Categoria category);
}
