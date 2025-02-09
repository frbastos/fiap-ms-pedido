package br.com.fiap.pedido.pedido.infra.persistence;

import java.math.BigDecimal;

import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_pedido")
public class ItemPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;

    @DecimalMin("0.00")
    @JoinColumn(name = "preco_unitario")
    private BigDecimal precoUnitario;

    private int quantidade;

    private String observacao;

    @DecimalMin("0.00")
    @JoinColumn(name = "preco_total")
    private BigDecimal precoTotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

}
