package br.com.fiap.pedido.cliente.application.usecase;

import java.util.Optional;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;

public interface BuscarClienteUseCase {

    Optional<Cliente> buscarPorDocumento(String document);

}
