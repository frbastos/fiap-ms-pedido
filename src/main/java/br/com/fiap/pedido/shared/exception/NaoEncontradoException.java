package br.com.fiap.pedido.shared.exception;

import lombok.Getter;

@Getter
public class NaoEncontradoException extends RuntimeException {

    private final String mensagem;

    public NaoEncontradoException() {
        this.mensagem = "Não encontrado";
    }

    public NaoEncontradoException(String mensagem) {
        this.mensagem = mensagem;
    }

}
