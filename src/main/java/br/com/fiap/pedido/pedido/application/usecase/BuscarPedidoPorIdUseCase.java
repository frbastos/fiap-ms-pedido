package br.com.fiap.pedido.pedido.application.usecase;

import java.util.Optional;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;

public interface BuscarPedidoPorIdUseCase {

    Optional<Pedido> buscar(Long id);

}
