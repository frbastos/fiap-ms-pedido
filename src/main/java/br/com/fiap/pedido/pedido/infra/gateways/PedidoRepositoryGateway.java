package br.com.fiap.pedido.pedido.infra.gateways;

import java.util.List;
import java.util.Optional;

import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.gateways.mappers.PedidoEntityMapper;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoEntity;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoRepository;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class PedidoRepositoryGateway implements PedidoGateway {
    private final PedidoRepository repository;

    @PersistenceContext
    private final EntityManager entityManager;

    public PedidoRepositoryGateway(PedidoRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Pedido> listarTodos() {
        List<PedidoEntity> ordersEntities = this.repository.findAll();
        return PedidoEntityMapper.toObjects(ordersEntities);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) throws NaoEncontradoException {
        return this.repository.findById(id).map(PedidoEntityMapper::toObject);
    }

    @Override
    public Pedido salvar(Pedido order) {
        PedidoEntity orderEntity = PedidoEntityMapper.toEntity(order);
        orderEntity = this.repository.save(orderEntity);
        return PedidoEntityMapper.toObject(orderEntity);
    }

    @Override
    public Pedido salvarEAtualizar(Pedido order) {
        PedidoEntity orderEntity = PedidoEntityMapper.toEntity(order);
        orderEntity = this.repository.saveAndFlush(orderEntity);
        return PedidoEntityMapper.toObject(orderEntity);
    }

    @Override
    public Optional<Pedido> buscarPorNumeroPedido(Long orderNumber) throws NaoEncontradoException {
        return this.repository.findByNumeroPedido(orderNumber).map(PedidoEntityMapper::toObject);
    }

    @Override
    public List<Pedido> listarTodosOrdenados() {
        List<PedidoEntity> orderDesc = this.repository.findAllNumeroPedido();
        return PedidoEntityMapper.toObjects(orderDesc);
    }

    @Override
    public Long buscarNumeroPedido() {
        Query query = entityManager.createNativeQuery("SELECT nextval('sequencia_numero_pedido')");
        return (Long) query.getSingleResult();
    }

}
