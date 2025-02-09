package br.com.fiap.pedido.produto.application;

import java.util.Optional;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;


public interface BuscarProdutoPorIdUseCase {

    Optional<Produto> buscarPorId(Long id) throws NaoEncontradoException;

}
