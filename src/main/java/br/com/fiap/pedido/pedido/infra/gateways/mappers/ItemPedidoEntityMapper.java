package br.com.fiap.pedido.pedido.infra.gateways.mappers;

import br.com.fiap.pedido.pedido.domain.entities.ItemPedido;
import br.com.fiap.pedido.pedido.infra.persistence.ItemPedidoEntity;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.infra.gateways.mappers.ProdutoEntityMapper;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;

public class ItemPedidoEntityMapper {

    public static ItemPedido toObject(ItemPedidoEntity entity) {

        Produto product = ProdutoEntityMapper.toObject(entity.getProduto());

        return new ItemPedido(
                entity.getId(),
                product,
                entity.getPrecoUnitario(),
                entity.getQuantidade(),
                entity.getObservacao(),
                entity.getPrecoTotal()
        );
    }

    public static ItemPedidoEntity toEntity(ItemPedido orderItem) {

        ProdutoEntity product = ProdutoEntityMapper.toEntity(orderItem.getProduto());

        return new ItemPedidoEntity(
                orderItem.getId(),
                product,
                orderItem.getPrecoUnitario(),
                orderItem.getQuantidade(),
                orderItem.getObservacao(),
                orderItem.getPrecoTotal(),
                null
        );
    }

}
