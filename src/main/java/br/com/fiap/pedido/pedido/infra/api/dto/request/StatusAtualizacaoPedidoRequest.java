package br.com.fiap.pedido.pedido.infra.api.dto.request;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;

public record StatusAtualizacaoPedidoRequest(
        StatusPedido state) {
}
