package br.com.fiap.pedido.cliente.infra.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.shared.ClienteHelper;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClienteRepositoryGatewayIT {

    @Autowired
    private ClienteRepositoryGateway clienteGateway;

    @Test
    void devePermitirSalvarCliente() {

        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();

        // Act
        Cliente result = clienteGateway.salvar(cliente);

        // Assert
        assertNotNull(result);
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getDocumento(), result.getDocumento());
        assertEquals(cliente.getEmail(), result.getEmail());
    }

    @Test
    void devePermitirBuscarClientePorDocumento() {

        Cliente cliente = ClienteHelper.gerarObjeto();
        Cliente clienteRegistrado = clienteGateway.salvar(cliente);

        Optional<Cliente> clienteEncontrado = clienteGateway.buscarPorDocumento(cliente.getDocumento());

        assertThat(clienteEncontrado)
                .isPresent()
                .get()
                .satisfies(c -> {
                    assertThat(c.getId()).isEqualTo(clienteRegistrado.getId());
                    assertThat(c.getNome()).isEqualTo(clienteRegistrado.getNome());
                    assertThat(c.getDocumento()).isEqualTo(clienteRegistrado.getDocumento());
                    assertThat(c.getEmail()).isEqualTo(clienteRegistrado.getEmail());
                });
    }
}
