package br.com.fiap.pedido.pedido.infra.feignclient.dto;

public record ItemPedidoCozinhaRequest(String produto,
        int quantidade,
        String observacao) {
}
