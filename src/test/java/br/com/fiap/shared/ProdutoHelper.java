package br.com.fiap.shared;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public class ProdutoHelper {

    public static Produto gerarXSalada() {
        return new Produto(1L, "X-Salada", new BigDecimal(15), Categoria.LANCHE);
    }

    public static Produto gerarBatataFrita() {
        return new Produto(2L, "Batata-Frita", new BigDecimal(10), Categoria.ACOMPANHAMENTO);
    }

    public static Produto gerarCocaCola() {
        return new Produto(3L, "Cola-Cola", new BigDecimal(7), Categoria.BEBIDA);
    }

    public static Produto gerarPudim() {
        return new Produto(4L, "Pudim", new BigDecimal(4), Categoria.SOBREMESA);
    }

}
