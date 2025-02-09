package br.com.fiap.pedido.produto.infra.api.dto;

import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;

public class ProdutoCommandMapper {

    public AtualizaProdutoCommand toAtualizaProdutoCommand(Long id, AtualizaProdutoRequest request){
        return new AtualizaProdutoCommand(id, request.descricao(), request.preco(), request.categoria());
    }

}
