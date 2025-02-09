package br.com.fiap.pedido.pedido.infra.api.dto.response;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;

public record StatusPagamentoPedidoResponse(
        Long orderNumber,
        StatusConfirmacaoPagamento statusConfirmacaoPagamento) {

}
