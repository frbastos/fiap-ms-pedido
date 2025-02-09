package br.com.fiap.pedido.pedido.domain.valueobjects;

public record InformacoesPagamento(
        String transacaoId,
        StatusConfirmacaoPagamento statusConfirmacaoPagamento) {
}
