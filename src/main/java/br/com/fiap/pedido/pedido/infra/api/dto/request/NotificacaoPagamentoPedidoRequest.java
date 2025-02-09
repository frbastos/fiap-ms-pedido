package br.com.fiap.pedido.pedido.infra.api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public record NotificacaoPagamentoPedidoRequest(
    @NotNull Long orderId,
    BigDecimal amount,
    LocalDateTime time,
    String transactionId,
    @NotNull StatusPagamento paymentStatus
) {

}
