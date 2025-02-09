package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public interface EnviarPedidoCozinhaUseCase {

    void enviar(Pedido pedido);

}
