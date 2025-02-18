package br.com.fiap.pedido.cliente.infra.persistence;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("SELECT c FROM ClienteEntity c WHERE c.documento = :documento")
    Optional<ClienteEntity> buscarPorDocumento(@Param("documento") String documento);

}
