package br.com.fiap.pedido.pedido.infra.api.dto.response;

import java.util.List;

public record PedidoAgrupoadoResponse(
        List<PedidoFollowUp> readyOrders,
        List<PedidoFollowUp> preparingOrders,
        List<PedidoFollowUp> receivedOrders
) {
}
