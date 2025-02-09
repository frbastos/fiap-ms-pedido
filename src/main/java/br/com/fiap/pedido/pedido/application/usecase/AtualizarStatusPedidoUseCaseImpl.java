package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public class AtualizarStatusPedidoUseCaseImpl implements AtualizarStatusPedidoUseCase {

     private final PedidoGateway pedidoGateway;
     
    public AtualizarStatusPedidoUseCaseImpl(
        PedidoGateway pedidoGateway){
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public Pedido atualizar(Long numeroPedido, StatusPedido status) {
        Pedido pedido = pedidoGateway.buscarPorNumeroPedido(numeroPedido).orElseThrow((NaoEncontradoException::new));
        pedido.atualizatStatus(status);
        return pedidoGateway.salvar(pedido);
    }

}
