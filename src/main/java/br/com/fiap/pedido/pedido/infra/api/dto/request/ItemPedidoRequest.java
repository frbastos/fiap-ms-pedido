package br.com.fiap.pedido.pedido.infra.api.dto.request;

public record ItemPedidoRequest(
        Long produtoId,
        int quantidade,
        String observacao
) {
}
