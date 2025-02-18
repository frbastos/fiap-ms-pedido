package br.com.fiap.pedido.pedido.infra.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.gateways.PedidoRepositoryGateway;
import br.com.fiap.pedido.pedido.infra.gateways.mappers.PedidoEntityMapper;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoEntity;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoRepository;
import br.com.fiap.shared.PedidoEntityHelper;
import br.com.fiap.shared.PedidoHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

class PedidoRepositoryGatewayTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PedidoRepository repository;

    @Mock
    private EntityManager entityManager;

    private PedidoRepositoryGateway pedidoRepositoryGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        pedidoRepositoryGateway = new PedidoRepositoryGateway(repository, entityManager);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void deveListarTodosPedidos() {
        // Arrange
        List<PedidoEntity> pedidoEntities = Arrays.asList(
                PedidoEntityHelper.gerar(),
                PedidoEntityHelper.gerar());
        when(repository.findAll()).thenReturn(pedidoEntities);

        // Act
        List<Pedido> result = pedidoRepositoryGateway.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void devePermitirBuscarPedidoPorId() {
        // Arrange
        PedidoEntity pedidoEntity = PedidoEntityHelper.gerar();

        when(repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));

        // Act
        Optional<Pedido> result = pedidoRepositoryGateway.buscarPorId(pedidoEntity.getId());

        // Assert
        assertTrue(result.isPresent());
        verify(repository).findById(pedidoEntity.getId());
    }

    @Test
    void deveRetornarProximoNumeroPedido() {
        Query query = mock(Query.class);

        when(entityManager.createNativeQuery("SELECT nextval('sequencia_numero_pedido')")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(42L);

        Long result = pedidoRepositoryGateway.buscarNumeroPedido();

        assertEquals(42L, result);
        verify(entityManager).createNativeQuery("SELECT nextval('sequencia_numero_pedido')");
        verify(query).getSingleResult();
    }

    @Test
    void devePetimirListarPedidosNaoFinalziados() {
        List<PedidoEntity> mockPedidos = Arrays.asList(
                PedidoEntityHelper.gerar(),
                PedidoEntityHelper.gerar());
        when(repository.findAllNaoFinalizados()).thenReturn(mockPedidos);

        List<Pedido> result = pedidoRepositoryGateway.listarTodosNaoFinalizados();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).findAllNaoFinalizados();
    }

    @Test
    void devePermitirBuscarPedidoPorNumeroPedido() {
        // Arrange
        Long orderNumber = 1010L;
        PedidoEntity pedidoEntity = PedidoEntityHelper.gerar();
        pedidoEntity.setNumeroPedido(orderNumber);

        when(repository.findByNumeroPedido(orderNumber)).thenReturn(Optional.of(pedidoEntity));

        // Act
        Optional<Pedido> result = pedidoRepositoryGateway.buscarPorNumeroPedido(orderNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderNumber, result.get().getNumeroPedido());
        verify(repository).findByNumeroPedido(orderNumber);
    }

    @Test
    void devePermitrSalvarEAtualizarPedido() {
        Pedido pedido = PedidoHelper.gerar();
        PedidoEntity pedidoEntity = PedidoEntityMapper.toEntity(pedido);

        when(repository.saveAndFlush(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido result = pedidoRepositoryGateway.salvarEAtualizar(pedido);

        verify(repository).saveAndFlush(any(PedidoEntity.class));
        assertNotNull(result);
    }

    @Test
    void devePermitirSalvarPedido() {
        // Arrange
        Pedido pedido = PedidoHelper.gerar();
        PedidoEntity pedidoEntity = PedidoEntityMapper.toEntity(pedido);
        when(repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        // Act
        Pedido result = pedidoRepositoryGateway.salvar(pedido);

        // Assert
        verify(repository).save(any(PedidoEntity.class));
        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
    }

}
