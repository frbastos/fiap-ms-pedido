package br.com.fiap.pedido.pedido.infra.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("SELECT o FROM PedidoEntity o WHERE o.numeroPedido = :numeroPedido")
    Optional<PedidoEntity> findByNumeroPedido(@Param("numeroPedido") Long numeroPedido);

    @Query(value = "SELECT * FROM pedido WHERE status <> 'FINALIZADO'", nativeQuery = true)
    List<PedidoEntity> findAllNaoFinalizados();
}
