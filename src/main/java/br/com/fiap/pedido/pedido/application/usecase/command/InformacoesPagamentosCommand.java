package br.com.fiap.pedido.pedido.application.usecase.command;

import java.math.BigDecimal;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;

public record InformacoesPagamentosCommand(
    Long pedidoId,
    String transacaoId,
    BigDecimal valor,
    StatusConfirmacaoPagamento statusPagamento
) {

}

