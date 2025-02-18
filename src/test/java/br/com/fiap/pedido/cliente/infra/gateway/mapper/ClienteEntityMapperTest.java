package br.com.fiap.pedido.cliente.infra.gateway.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.gateway.mappers.ClienteEntityMapper;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;
class ClienteEntityMapperTest {

    @Test
    void deveMapearCampos_Objeto() {
        ClienteEntity entity = new ClienteEntity(
            1L,
            "John Doe",
            "12345678900",
            "john@email.com"
        );

        Cliente result = ClienteEntityMapper.toObject(entity);

        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getNome());
        assertEquals("12345678900", result.getDocumento());
        assertEquals("john@email.com", result.getEmail());
    }

    @Test
    void deveMapearCampos_Entidade() {
        Cliente customer = new Cliente(
            1L,
            "Jane Doe",
            "09876543211",
            "jane@email.com"
        );

        ClienteEntity result = ClienteEntityMapper.toEntity(customer);

        assertEquals(1L, result.getId());
        assertEquals("Jane Doe", result.getNome());
        assertEquals("09876543211", result.getDocumento());
        assertEquals("jane@email.com", result.getEmail());
    }
}