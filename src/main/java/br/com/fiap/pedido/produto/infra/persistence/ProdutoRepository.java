package br.com.fiap.pedido.produto.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByCategoria(Categoria categoria);
}
