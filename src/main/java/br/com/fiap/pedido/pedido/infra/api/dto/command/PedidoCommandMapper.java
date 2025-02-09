package br.com.fiap.pedido.pedido.infra.api.dto.command;

import br.com.fiap.pedido.pedido.application.usecase.command.NotificacaoPagamentoPedidoCommand;
import br.com.fiap.pedido.pedido.application.usecase.command.PagamentoEnviadoCommand;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.api.dto.request.NotificacaoPagamentoPedidoRequest;

public class PedidoCommandMapper {

    public static NotificacaoPagamentoPedidoCommand toOrderPaymentNotificationCommand(NotificacaoPagamentoPedidoRequest request) {
        return new NotificacaoPagamentoPedidoCommand(request.orderId(), request.amount(), request.time(), request.transactionId(), request.paymentStatus());
    }

    public static PagamentoEnviadoCommand toSendPaymentQRCODECommand(Pedido order){
        return new PagamentoEnviadoCommand(order.getId(), order.getPrecoTotal());
    }

}
