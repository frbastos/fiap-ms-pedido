package br.com.fiap.pedido.cliente.application.usecase;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;

public interface RegistrarClienteUseCase {

    Cliente save(Cliente customerPersistence);

}
