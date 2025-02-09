package br.com.fiap.pedido.pedido.application.usecase;

import br.com.fiap.pedido.pedido.application.usecase.command.InformacoesPagamentosCommand;

public interface ProcessarPagamentoPedidoUseCase {

    public void processar(InformacoesPagamentosCommand command);

}
