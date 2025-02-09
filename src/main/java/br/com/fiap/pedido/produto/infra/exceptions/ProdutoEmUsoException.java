package br.com.fiap.pedido.produto.infra.exceptions;

public class ProdutoEmUsoException extends RuntimeException{

    public ProdutoEmUsoException(){
        super("Produto em uso");
    }

}
