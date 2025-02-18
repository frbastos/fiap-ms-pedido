package br.com.fiap.pedido.pedido.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoEntity> itens;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @DecimalMin("0.00")
    @JoinColumn(name = "peco_total")
    private BigDecimal precoTotal;

    @JoinColumn(name = "transacao_pagamento_id")
    private String transacaoPagamentoId;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "status_confirmacao_pagamento")
    private StatusConfirmacaoPagamento statusConfirmacaoPagamento;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private Long numeroPedido;

}
