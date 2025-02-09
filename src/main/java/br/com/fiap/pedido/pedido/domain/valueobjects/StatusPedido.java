package br.com.fiap.pedido.pedido.domain.valueobjects;

public enum StatusPedido {
    PENDENTE,    // Pedido aguardando confirmação de pagamento para ir para cozinha
    RECEBIDO,   // Pedido recebido pela cozinha após a confirmação de pagamento
    PREPARANDO,  // Pedido está sendo preparado
    PRONTO,      // Pedido finalizado pelo cozinha
    FINALIZADO    // Pedido entregue para o cliente
}
