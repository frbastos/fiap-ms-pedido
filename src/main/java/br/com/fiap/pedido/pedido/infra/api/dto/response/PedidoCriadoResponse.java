package br.com.fiap.pedido.pedido.infra.api.dto.response;

public record PedidoCriadoResponse(
        Long numeroPedido,
        String qrcode) {

}
