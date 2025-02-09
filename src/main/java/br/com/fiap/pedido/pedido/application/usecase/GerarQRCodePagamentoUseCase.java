package br.com.fiap.pedido.pedido.application.usecase;

import java.math.BigDecimal;

public interface GerarQRCodePagamentoUseCase {

    String gerar(Long pedidoId, BigDecimal valor);
}
