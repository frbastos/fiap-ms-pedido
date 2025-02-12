package br.com.fiap.pedido.cliente.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void devePermitirCriarClenteCOmTodosCampos() {
        Long id = 1L;
        String nome = "John Doe";
        String documento = "12345678900";
        String email = "john@email.com";

        Cliente cliente = Cliente.builder()
                .id(id)
                .nome(nome)
                .documento(documento)
                .email(email)
                .build();

        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(documento, cliente.getDocumento());
        assertEquals(email, cliente.getEmail());
    }

}
