package br.com.fiap.shared;

import java.math.BigDecimal;
import java.util.random.RandomGenerator;

import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;

public class ProdutoEntiytHelper {

    public static ProdutoEntity gerar(){
        Long id = geradorId();
        return new ProdutoEntity(id, "Produto " + id, BigDecimal.valueOf(15), Categoria.LANCHE);
    }

     private static Long geradorId(){
        return RandomGenerator.getDefault().nextLong();
    }

}
