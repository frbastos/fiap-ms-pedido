package br.com.fiap.pedido.cliente.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.shared.ClienteHelper;

class BuscarClienteUseCaseTest {

    private AutoCloseable openMocks;
    private BuscarClienteUseCaseImpl usecase;

    @Mock
    private ClienteGateway clienteGateway;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        usecase = new BuscarClienteUseCaseImpl(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarCliente() {

        // Arrange
        String documento = "00.000.000-00";
        Cliente cliente = ClienteHelper.gerarObjeto();

        when(usecase.buscarPorDocumento(any(String.class)))
                .thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> clienteObtido = usecase.buscarPorDocumento(documento);

        // Assert
        verify(clienteGateway).buscarPorDocumento(documento);
        assertThat(clienteObtido)
                .isPresent()
                .get()
                .satisfies(c -> {
                    assertThat(c.getNome()).isEqualTo("Fellipe Bastos");
                    assertThat(c.getDocumento()).isEqualTo("00.000.000-00");
                    assertThat(c.getEmail()).isEqualTo("fbastos95@gmail.com");
                });
    }

    @Test
    void deveRetornarVazio_QuandoDocumento_Null() {
        // Arrange
        String documento = null;
        when(clienteGateway.buscarPorDocumento(documento)).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> result = usecase.buscarPorDocumento(documento);

        // Arrange
        assertThat(result).isEmpty();
        verify(clienteGateway).buscarPorDocumento(documento);
    }

}
