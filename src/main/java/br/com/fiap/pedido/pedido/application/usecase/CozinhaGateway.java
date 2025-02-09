package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public interface CozinhaGateway {

    void registrarPedido(Pedido pedido);

}
