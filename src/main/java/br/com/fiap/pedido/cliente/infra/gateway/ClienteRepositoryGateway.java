package br.com.fiap.pedido.cliente.infra.gateway;

import java.util.Optional;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.gateway.mappers.ClienteEntityMapper;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteRepository;

public class ClienteRepositoryGateway implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteRepositoryGateway(
        ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente salvar(Cliente customer) {
        ClienteEntity entity = ClienteEntityMapper.toEntity(customer);
        entity = this.clienteRepository.save(entity);
        return ClienteEntityMapper.toObject(entity);
    }

    @Override
    public Optional<Cliente> buscarPorDocumento(String document) {
        return this.clienteRepository.buscarPorDocumento(document).map(ClienteEntityMapper::toObject);
    }

}
