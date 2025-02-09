package br.com.fiap.pedido.pedido.infra.mapper;

import java.util.Comparator;
import java.util.List;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoAgrupoadoResponse;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoFollowUp;

public class PedidoAgrupadoMapper {

    public static PedidoAgrupoadoResponse toPedidos(List<Pedido> orders){
        List<PedidoFollowUp> orderFollowUps = orders.stream().map(PedidoAgrupadoMapper::toPedidoFollowUp).toList();

        List<PedidoFollowUp> readyOrders =
                orderFollowUps
                        .stream()
                        .filter(order -> StatusPedido.PRONTO.toString().equals(order.status()))
                        .sorted(Comparator.comparing(PedidoFollowUp::dateCreation)).toList();

        List<PedidoFollowUp> preparingOrders =
                orderFollowUps
                        .stream()
                        .filter(order -> StatusPedido.PREPARANDO.toString().equals(order.status()))
                        .sorted(Comparator.comparing(PedidoFollowUp::dateCreation)).toList();

        List<PedidoFollowUp> receivedOrders =
                orderFollowUps
                        .stream()
                        .filter(order -> StatusPedido.RECEBIDO.toString().equals(order.status()))
                        .sorted(Comparator.comparing(PedidoFollowUp::dateCreation)).toList();

        return new PedidoAgrupoadoResponse(readyOrders, preparingOrders, receivedOrders);
    }

    public static PedidoFollowUp toPedidoFollowUp(Pedido pedido){
        return new PedidoFollowUp(pedido.getNumeroPedido(),pedido.getStatus().toString(), pedido.getDataCriacao().toString());
    }
}
