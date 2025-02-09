package br.com.fiap.pedido.pedido.domain.entities;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import lombok.Getter;

@Getter
public class ItemPedido {

    private Long id;
    private Produto produto;
    private BigDecimal precoUnitario;
    private int quantidade;
    private String observacao;
    private BigDecimal precoTotal;

    public ItemPedido(
            Long id, 
            Produto produto, 
            BigDecimal precoUnitario, 
            int quantidade, 
            String observacao,
            BigDecimal precoTotal) {
        this.id = id;
        this.produto = produto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.precoTotal = precoTotal;
    }

    public ItemPedido(
            Long id,
            Produto produto,
            int quantidade,
            String observacao
    ) {
        this(id, produto, produto.getPreco(), quantidade, observacao, BigDecimal.ZERO); 
        this.precoTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));      
    }

}
