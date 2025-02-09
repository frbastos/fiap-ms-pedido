package br.com.fiap.pedido.produto.infra.gateways.mappers;

import java.util.List;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;

public class ProdutoEntityMapper {

    public static ProdutoEntity toEntity(Produto produto) {
        return new ProdutoEntity(
                produto.getId(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria());
    }

    public static List<ProdutoEntity> toListaProdutosEntity(List<Produto> products) {
        return products
            .stream()
            .map(ProdutoEntityMapper::toEntity)
            .toList();
    }

    public static Produto toObject(ProdutoEntity productEntity) {
        return new Produto(
                productEntity.getId(),
                productEntity.getDescricao(),
                productEntity.getPreco(),
                productEntity.getCategoria());
    }

    public static List<Produto> toListaProdutos(List<ProdutoEntity> productsEntity) {
        return productsEntity
            .stream()
            .map(ProdutoEntityMapper::toObject)
            .toList();
    }

}
