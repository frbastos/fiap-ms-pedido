package br.com.fiap.pedido.cliente.infra.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.gateway.mappers.ClienteEntityMapper;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteEntity;
import br.com.fiap.pedido.cliente.infra.persistence.ClienteRepository;
import br.com.fiap.shared.ClienteHelper;

class ClienteRepositoryGatewayTest {

    @Mock
    private ClienteRepository clienteRepository;
    private ClienteRepositoryGateway gateway;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        gateway = new ClienteRepositoryGateway(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirSalvarCliente() {

        // Arrange
        Cliente cliente = ClienteHelper.gerarObjeto();
        ClienteEntity entidadeEsperada = ClienteEntityMapper.toEntity(cliente);

        when(clienteRepository.save(any(ClienteEntity.class)))
            .thenReturn(entidadeEsperada);

        // Act
        Cliente result = gateway.salvar(cliente);

        // Assert
        verify(clienteRepository).save(any(ClienteEntity.class));
        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getDocumento(), result.getDocumento());
        assertEquals(cliente.getEmail(), result.getEmail());
    }

    @Test
    void devePermitirBuscarClientePorDocumento(){
        
        // Arrange
        String documento = "00.000.000-00";
        Cliente cliente = ClienteHelper.gerarObjeto();
        ClienteEntity clienteEntity = ClienteEntityMapper.toEntity(cliente);

        when(clienteRepository.buscarPorDocumento(any(String.class)))
                .thenReturn(Optional.of(clienteEntity));

        // Act
        Optional<Cliente> clienteObtido = gateway.buscarPorDocumento(documento);

        // Assert
        verify(clienteRepository).buscarPorDocumento(documento);
        assertThat(clienteObtido)
                .isPresent()
                .get()
                .satisfies(c -> {
                    assertThat(c.getId()).isEqualTo(cliente.getId());
                    assertThat(c.getNome()).isEqualTo(cliente.getNome());
                    assertThat(c.getDocumento()).isEqualTo(cliente.getDocumento());
                    assertThat(c.getEmail()).isEqualTo(cliente.getEmail());
                });
    }
}
