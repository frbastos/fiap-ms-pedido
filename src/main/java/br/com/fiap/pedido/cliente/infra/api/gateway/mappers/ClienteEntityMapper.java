package br.com.fiap.pedido.cliente.infra.api.gateway.mappers;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.api.persistence.ClienteEntity;

public class ClienteEntityMapper {

    public static Cliente toObject(ClienteEntity entity) {
        return new Cliente(
            entity.getId(), 
            entity.getNome(), 
            entity.getDocumento(), 
            entity.getEmail());
    }

    public static ClienteEntity toEntity(Cliente customer) {
        return new ClienteEntity(
            customer.getId(), 
            customer.getNome(), 
            customer.getDocumento(), 
            customer.getEmail());
    }

}
