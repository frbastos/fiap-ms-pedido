package br.com.fiap.pedido.cliente.application.usecase;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;

public class RegistrarClienteUseCaseImpl implements RegistrarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public RegistrarClienteUseCaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public Cliente save(Cliente customer) {
        return clienteGateway.salvar(customer);
    }

}
