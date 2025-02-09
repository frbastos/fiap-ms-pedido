package br.com.fiap.pedido.pedido.application.usecase.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPagamento;

public record NotificacaoPagamentoPedidoCommand(
    Long orderId,
    BigDecimal amount,
    LocalDateTime time,
    String transactionId,
    StatusPagamento paymentStatus
) {

}
