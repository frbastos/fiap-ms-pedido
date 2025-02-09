package br.com.fiap.pedido.pedido.application.gateways;

import java.util.List;
import java.util.Optional;

import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;

public interface PedidoGateway {

    List<Pedido> listarTodos();

    Optional<Pedido> buscarPorId(Long id) throws NaoEncontradoException;

    Pedido salvar(Pedido order);

    Pedido salvarEAtualizar(Pedido order);

    Optional<Pedido> buscarPorNumeroPedido(Long numeroPedido) throws NaoEncontradoException;

    List<Pedido> listarTodosOrdenados();

    Long buscarNumeroPedido();

}
