package br.com.fiap.pedido.cliente.application.usecase;

import java.util.Optional;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;

public class BuscarClienteUseCaseImpl implements BuscarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public BuscarClienteUseCaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clienteGateway.buscarPorDocumento(documento);
    }

}
