package br.com.fiap.pedido.pedido.application.exception;

public class ErroAoGerarQRCodeException extends RuntimeException{

    public ErroAoGerarQRCodeException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }

}
