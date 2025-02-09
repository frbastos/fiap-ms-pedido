package br.com.fiap.pedido.pedido.application.gateways;

import java.math.BigDecimal;

public interface PagamentoGateway {

    String gerar(Long pedidoId, BigDecimal valor);

}
