package br.com.fiap.pedido.pedido.infra.mapper;

import br.com.fiap.pedido.cliente.infra.mapper.ClienteDTOMapper;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoResponse;

public class PedidoDTOMapper {

    public static PedidoResponse toOrderResponse(Pedido pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getDataCriacao(),
                ClienteDTOMapper.toResponse(pedido.getCliente()), pedido.getStatus(),
                pedido.getPrecoTotal(), pedido.getInformacoesPagamento().statusConfirmacaoPagamento(), pedido.getNumeroPedido());
    }

}
