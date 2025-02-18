package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.application.usecase.command.InformacoesPagamentosCommand;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public class ProcessarPagamentoPedidoUseCaseImpl implements ProcessarPagamentoPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;
    private final EnviarPedidoCozinhaUseCase enviarPedidoCozinhaUseCase;

    public ProcessarPagamentoPedidoUseCaseImpl(
            PedidoGateway pedidoGateway,
            BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase,
            EnviarPedidoCozinhaUseCase enviarPedidoCozinhaUseCase) {
        this.pedidoGateway = pedidoGateway;
        this.buscarPedidoPorIdUseCase = buscarPedidoPorIdUseCase;
        this.enviarPedidoCozinhaUseCase = enviarPedidoCozinhaUseCase;
    }

    @Override
    public void processar(InformacoesPagamentosCommand command) {
        Pedido pedido = buscarPedidoPorIdUseCase.buscar(command.pedidoId())
                .orElseThrow(
                        () -> new NaoEncontradoException("Pedido não encontrado com esse ID: " + command.pedidoId()));

        pedido.atualizaInformacoesPagamento(command.transacaoId(), command.statusPagamento());
        pedido = pedidoGateway.salvar(pedido);

        if (pedido.isAprovado()) {
            enviarPedidoCozinhaUseCase.enviar(pedido);
        }
    }

}
