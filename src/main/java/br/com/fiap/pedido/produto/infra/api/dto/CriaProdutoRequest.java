package br.com.fiap.pedido.produto.infra.api.dto;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public record CriaProdutoRequest(
                String descricao,
                BigDecimal preco,
                Categoria categoria) {
}
