package br.com.fiap.pedido.cliente.domain.entities;

import lombok.Getter;

@Getter
public class Cliente {

    private Long id;
    private String nome;
    private String documento;
    private String email;

    public Cliente(Long id, String nome, String documento, String email) {
        this.id = id;
        this.email = email;
        this.documento = documento;
        this.nome = nome;
    }

}
