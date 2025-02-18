package br.com.fiap.pedido.cliente.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.shared.ClienteHelper;

class RegistrarClienteUseCaseTest {

    private AutoCloseable openMocks;
    private RegistrarClienteUseCaseImpl usecase;

    @Mock
    private ClienteGateway clienteGateway;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        usecase = new RegistrarClienteUseCaseImpl(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRegistrarCliente() {
        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();

        when(usecase.save(any(Cliente.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        Cliente clienteCriado = usecase.save(cliente);

        // Assert
        verify(clienteGateway).salvar(clienteCriado);
        assertThat(clienteCriado)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteCriado.getId())
                .isEqualTo(cliente.getId());
        assertThat(clienteCriado.getDocumento())
                .isEqualTo(cliente.getDocumento());
        assertThat(clienteCriado.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(clienteCriado.getNome()).isEqualTo(cliente.getNome());
    }

}
