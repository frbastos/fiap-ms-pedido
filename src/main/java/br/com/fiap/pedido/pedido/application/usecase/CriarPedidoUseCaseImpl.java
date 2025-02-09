package br.com.fiap.pedido.pedido.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.domain.entities.ItemPedido;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.InformacoesPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.dto.request.ItemPedidoRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import br.com.fiap.pedido.shared.validations.StringValidator;

public class CriarPedidoUseCaseImpl implements CriarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final BuscarProdutoPorIdUseCase buscarProdutoPorId;

    public CriarPedidoUseCaseImpl(
            PedidoGateway pedidoGateway,
            BuscarClienteUseCase buscarClienteUseCase,
            BuscarProdutoPorIdUseCase buscarProdutoPorId) {

        this.pedidoGateway = pedidoGateway;
        this.buscarProdutoPorId = buscarProdutoPorId;
        this.buscarClienteUseCase = buscarClienteUseCase;

    }

    @Override
    public Pedido criar(PedidoRequest persistence) {

        Cliente cliente = buscarCliente(persistence.documento()).orElse(null);

        List<ItemPedido> ordersItem = criarItensPedido(persistence.itens());

        Long numeroPedido = pedidoGateway.buscarNumeroPedido();

        InformacoesPagamento informacoesPagamento = new InformacoesPagamento(null, StatusConfirmacaoPagamento.PENDENTE);

        Pedido pedido = new Pedido(
                null,
                cliente,
                ordersItem,
                StatusPedido.PENDENTE,
                LocalDateTime.now(),
                numeroPedido,
                informacoesPagamento);

        return pedidoGateway.salvarEAtualizar(pedido);
    }

    private Optional<Cliente> buscarCliente(String documento) {
        if (StringValidator.isNullOrEmpty(documento))
            return Optional.empty();
        return buscarClienteUseCase.buscarPorDocumento(documento);
    }

    private List<ItemPedido> criarItensPedido(List<ItemPedidoRequest> items) {

        return items.stream().map(item -> {
            Produto produtoEncontrado = buscarProdutoPorId.buscarPorId(item.produtoId())
                    .orElseThrow(
                            () -> new NaoEncontradoException("Produto não encontrado com ID: " + item.produtoId()));

            return new ItemPedido(
                    null,
                    produtoEncontrado,
                    item.quantidade(),
                    item.observacao());

        }).toList();
    }
}
