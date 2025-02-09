package br.com.fiap.pedido.produto.application.gateway;

import java.util.List;
import java.util.Optional;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public interface ProdutoGateway {

    List<Produto> listarTodos();

    Optional<Produto> buscarPorId(Long id) throws NaoEncontradoException;

    List<Produto> buscarPorCategoria(Categoria categoria);

    void remover(Long id);

    Produto salvar(Produto product);

}
