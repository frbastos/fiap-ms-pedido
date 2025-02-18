package br.com.fiap.shared;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.gateway.mappers.ClienteEntityMapper;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;

public class ClienteHelper {

    public static Cliente gerarObjeto() {
        return Cliente.builder()
                .id(null)
                .nome("Fellipe Bastos")
                .documento("00.000.000-00")
                .email("fbastos95@gmail.com")
                .build();
    }

    public static ClienteEntity gerarEntity() {
        Cliente objeto = gerarObjeto();
        return ClienteEntityMapper.toEntity(objeto);
    }

}
