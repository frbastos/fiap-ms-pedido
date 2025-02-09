package br.com.fiap.pedido.pedido.infra.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.pedido.cliente.infra.api.dto.ClienteResponse;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;

public record PedidoResponse(
        Long id,
        LocalDateTime dataCriacao,
        ClienteResponse cliente,
        StatusPedido status,
        BigDecimal precoTotal,
        StatusConfirmacaoPagamento statusConfirmacaoPagamento,
        Long numeroPedido) {

}
