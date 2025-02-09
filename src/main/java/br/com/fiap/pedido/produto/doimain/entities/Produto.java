package br.com.fiap.pedido.produto.doimain.entities;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.shared.validations.NumberValidator;
import br.com.fiap.pedido.shared.validations.StringValidator;
import lombok.Getter;

@Getter
public class Produto {

    private Long id;
    private String descricao;
    private BigDecimal preco;
    private Categoria categoria;

    public Produto(Long id, String descricao, BigDecimal preco, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.validar();
    }

    public Produto(String descricao, BigDecimal preco, Categoria categoria) {
        this(null, descricao, preco, categoria);
    }

    private void validar() {
        if (StringValidator.isNullOrEmpty(descricao)) {
            throw new IllegalArgumentException("descrição não pode ser nulo ou vazio");
        }
        if (NumberValidator.isNegative(preco)) {
            throw new IllegalArgumentException("preço não pode ser nulo ou negativo");
        }
        if (getCategoria() == null) {
            throw new IllegalArgumentException("categoria não pode ser nula");
        }
    }

    public void atualizar(AtualizaProdutoCommand update) {
        this.descricao = update.descricao();
        this.preco = update.preco();
        this.categoria = update.categoria();
        this.validar();
    }
}
