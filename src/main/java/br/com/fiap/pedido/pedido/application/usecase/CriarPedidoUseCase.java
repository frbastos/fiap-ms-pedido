package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;

public interface CriarPedidoUseCase {

    Pedido criar(PedidoRequest persistence);

}
