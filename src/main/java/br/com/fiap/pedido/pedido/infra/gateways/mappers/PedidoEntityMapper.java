package br.com.fiap.pedido.pedido.infra.gateways.mappers;

import java.util.List;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.gateway.mappers.ClienteEntityMapper;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;
import br.com.fiap.pedido.pedido.domain.entities.ItemPedido;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.InformacoesPagamento;
import br.com.fiap.pedido.pedido.infra.persistence.ItemPedidoEntity;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoEntity;

public class PedidoEntityMapper {

        public static Pedido toObject(PedidoEntity entity) {

                Cliente customer = entity.getCliente() != null
                                ? ClienteEntityMapper.toObject(entity.getCliente())
                                : null;

                InformacoesPagamento informacoesPagamento = new InformacoesPagamento(entity.getTransacaoPagamentoId(),
                                entity.getStatusConfirmacaoPagamento());

                return new Pedido(
                                entity.getId(),
                                customer,
                                toItemPedido(entity),
                                entity.getStatus(),
                                entity.getDataCriacao(),
                                entity.getNumeroPedido(),
                                informacoesPagamento);

        }

        public static PedidoEntity toEntity(Pedido order) {

                ClienteEntity customerEntity = order.getCliente() != null
                                ? ClienteEntityMapper.toEntity(order.getCliente())
                                : null;

                PedidoEntity orderEntity = new PedidoEntity(
                                order.getId(),
                                customerEntity,
                                null,
                                order.getStatus(),
                                order.getPrecoTotal(),
                                order.getInformacoesPagamento().transacaoId(),
                                order.getInformacoesPagamento().statusConfirmacaoPagamento(),
                                order.getDataCriacao(), order.getNumeroPedido());

                List<ItemPedidoEntity> ordersItemEntity = order.getItens()
                                .stream()
                                .map(ItemPedidoEntityMapper::toEntity)
                                .peek(orderItemEntity -> orderItemEntity.setPedido(orderEntity))
                                .toList();

                orderEntity.setItens(ordersItemEntity);

                return orderEntity;
        }

        public static List<ItemPedido> toItemPedido(PedidoEntity orderEntity) {
                return orderEntity.getItens().stream().map(ItemPedidoEntityMapper::toObject)
                                .toList();
        }

        public static List<Pedido> toObjects(List<PedidoEntity> list) {
                return list.stream().map(PedidoEntityMapper::toObject).toList();
        }

}
