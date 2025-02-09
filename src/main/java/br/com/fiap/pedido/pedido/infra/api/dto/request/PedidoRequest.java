package br.com.fiap.pedido.pedido.infra.api.dto.request;

import java.util.List;

public record PedidoRequest(
        String documento,
        List<ItemPedidoRequest> itens
) {
}