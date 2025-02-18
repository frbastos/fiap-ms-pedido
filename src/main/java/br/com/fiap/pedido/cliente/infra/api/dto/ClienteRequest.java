package br.com.fiap.pedido.cliente.infra.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
                @NotBlank String nome,
                @NotBlank @Email String email,
                @NotBlank String documento) {
}
