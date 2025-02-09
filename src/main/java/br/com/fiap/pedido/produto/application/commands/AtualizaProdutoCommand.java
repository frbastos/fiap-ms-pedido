package br.com.fiap.pedido.produto.application.commands;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public record AtualizaProdutoCommand(
    Long id,
    String descricao,
    BigDecimal preco,
    Categoria categoria
) {

}
