package br.com.fiap.pedido.cliente.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

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
class RegistrarClienteUseCaseIT {

    @Autowired
    private RegistrarClienteUseCase registrarClienteUseCase;

    @Test
    void deveRegistrarCliente() {

        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();

        // Act
        Cliente clienteCriado = registrarClienteUseCase.save(cliente);

        // Asert
        assertThat(clienteCriado)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteCriado.getId())
                .isNotNull();
        assertThat(clienteCriado.getDocumento())
                .isEqualTo(cliente.getDocumento());
        assertThat(clienteCriado.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(clienteCriado.getNome()).isEqualTo(cliente.getNome());
    }

}
