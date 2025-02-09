package br.com.fiap.pedido.pedido.infra.api.dto.response;

public record PedidoFollowUp(
    Long orderNumber,
    String status,
    String dateCreation
){}
