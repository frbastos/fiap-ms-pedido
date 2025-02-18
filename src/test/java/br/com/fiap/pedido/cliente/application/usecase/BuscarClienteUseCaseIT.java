package br.com.fiap.pedido.cliente.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

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
class BuscarClienteUseCaseIT {

    @Autowired
    private BuscarClienteUseCase buscarClienteUseCase;

    @Autowired
    private RegistrarClienteUseCase registrarClienteUseCase;

    @Test
    void devePermitirBuscarClientePorDocumento() {
        Cliente cliente = ClienteHelper.gerarObjeto();
        Cliente clienteRegistrado = registrarClienteUseCase.save(cliente);

        Optional<Cliente> clienteEncontrado = buscarClienteUseCase.buscarPorDocumento(cliente.getDocumento());

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
