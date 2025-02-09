package br.com.fiap.pedido.cliente.application.gateway;

import java.util.Optional;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;

public interface ClienteGateway {

    Cliente salvar (Cliente cliente);
    Optional<Cliente> buscarPorDocumento( String documento);

}
