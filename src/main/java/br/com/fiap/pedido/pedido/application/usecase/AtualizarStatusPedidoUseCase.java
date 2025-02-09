package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;

public interface AtualizarStatusPedidoUseCase {

    Pedido atualizar(Long id, StatusPedido status);

}
