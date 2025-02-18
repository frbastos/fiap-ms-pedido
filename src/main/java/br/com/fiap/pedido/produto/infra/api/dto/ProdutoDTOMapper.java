package br.com.fiap.pedido.produto.infra.api.dto;

import br.com.fiap.pedido.produto.doimain.entities.Produto;

public class ProdutoDTOMapper {

    public static Produto toProduct(CriaProdutoRequest request){
        return new Produto(null, request.descricao() , request.preco(), request.categoria());
    }

    public static ProdutoResponse toResponse(Produto produto){
        return new ProdutoResponse(produto.getId(), produto.getDescricao(), produto.getPreco(), produto.getCategoria());
    }
    
}
