package br.com.fiap.pedido.pedido.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.pedido.domain.valueobjects.InformacoesPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import lombok.Getter;

@Getter
public class Pedido {

    private Long id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedido status;
    private BigDecimal precoTotal;
    private InformacoesPagamento informacoesPagamento;
    private LocalDateTime dataCriacao;
    private Long numeroPedido;

    public Pedido(
            Long id,
            Cliente cliente,
            List<ItemPedido> itens,
            StatusPedido status,
            LocalDateTime dataCriacao,
            Long numeroPedido,
            InformacoesPagamento informacoesPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.numeroPedido = numeroPedido;
        this.informacoesPagamento = informacoesPagamento;
        this.precoTotal = this.calculateTotalPrice(itens);
    }

    public BigDecimal calculateTotalPrice(List<ItemPedido> itens) {
        return itens.stream().map(ItemPedido::getPrecoTotal).reduce(BigDecimal.ZERO,
                BigDecimal::add);
    }

    public void atualizatStatus(StatusPedido status) {
        if (status.equals(StatusPedido.PENDENTE)) {
            throw new IllegalArgumentException("Proíbido forçar status de pedido para pendente");
        } else {
            this.status = status;
        }
    }

    public void atualizaInformacoesPagamento(String transacaoId,
            StatusConfirmacaoPagamento statusConfirmacaoPagamento) {
        this.informacoesPagamento = new InformacoesPagamento(transacaoId, statusConfirmacaoPagamento);
        if (statusConfirmacaoPagamento.equals(StatusConfirmacaoPagamento.APROVADO)) {
            this.status = StatusPedido.RECEBIDO;
        }
    }

    public boolean isAprovado() {
        return informacoesPagamento != null 
            && informacoesPagamento.statusConfirmacaoPagamento().equals(StatusConfirmacaoPagamento.APROVADO);
    }

}
