package br.com.fiap.pedido.pedido.infra.feignclient.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QRCodeRequest(
    @NotBlank(message = "Descrição não pode vazia") String descricao,
    @NotNull @DecimalMin(value = "0.1", message = "Valor não pode ser inferior a R$ 0,01") BigDecimal valor,
    @NotBlank(message = "Url callback não pode ser vazia") String callbackUrl) {

}
