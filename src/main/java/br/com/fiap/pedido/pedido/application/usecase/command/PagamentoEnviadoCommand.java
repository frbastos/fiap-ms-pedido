package br.com.fiap.pedido.pedido.application.usecase.command;

import java.math.BigDecimal;

public record PagamentoEnviadoCommand(
    Long orderId,
    BigDecimal amount
) {

}
