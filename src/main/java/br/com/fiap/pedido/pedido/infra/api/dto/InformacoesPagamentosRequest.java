package br.com.fiap.pedido.pedido.infra.api.dto;

import java.math.BigDecimal;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;

public record InformacoesPagamentosRequest(
    String transacaoId,
    BigDecimal valor,
    StatusConfirmacaoPagamento statusPagamento
) {

}
