package br.com.fiap.pedido.cliente.infra.api.dto;

public record ClienteResponse(
    Long id, 
    String nome, 
    String documento, 
    String email) {
}
