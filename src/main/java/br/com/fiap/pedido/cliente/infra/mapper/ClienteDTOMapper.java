package br.com.fiap.pedido.cliente.infra.mapper;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.api.dto.ClienteRequest;
import br.com.fiap.pedido.cliente.infra.api.dto.ClienteResponse;

public class ClienteDTOMapper {

    public static ClienteResponse toResponse(Cliente cliente) {
        if(cliente == null) return null;
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getDocumento(), cliente.getEmail());
    }

    public static Cliente toCliente(ClienteRequest request) {
        return new Cliente(null, request.nome(), request.documento(), request.email());
    }

}
