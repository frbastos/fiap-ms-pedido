package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public class EnviarPedidoCozinhaUseCaseImpl implements EnviarPedidoCozinhaUseCase {

    private final CozinhaGateway cozinhaGateway;

    public EnviarPedidoCozinhaUseCaseImpl(CozinhaGateway cozinhaGateway) {
        this.cozinhaGateway = cozinhaGateway;
    }

    @Override
    public void enviar(Pedido pedido) {
        cozinhaGateway.registrarPedido(pedido);
    }

}
