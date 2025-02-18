package br.com.fiap.pedido.pedido.infra.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.pedido.pedido.application.exception.ErroAoGerarQRCodeException;
import br.com.fiap.pedido.pedido.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorNumeroPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.CriarPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.GerarQRCodePagamentoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ListarTodosPedidosUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ProcessarPagamentoPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.command.InformacoesPagamentosCommand;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.dto.InformacoesPagamentosRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoAgrupoadoResponse;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoCriadoResponse;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoResponse;
import br.com.fiap.pedido.pedido.infra.api.dto.response.StatusPagamentoPedidoResponse;
import br.com.fiap.pedido.pedido.infra.mapper.PedidoAgrupadoMapper;
import br.com.fiap.pedido.pedido.infra.mapper.PedidoDTOMapper;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/pedidos" })
@RequiredArgsConstructor
public class PedidoController {

    private final ListarTodosPedidosUseCase listarTodosPedidosUseCase;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final BuscarPedidoPorNumeroPedidoUseCase buscarPedidoPorNumeroPedidoUseCase;
    private final GerarQRCodePagamentoUseCase gerarQRCodePagamentoUseCase;
    private final ProcessarPagamentoPedidoUseCase processarPagamentoPedidoUseCase;

    private List<PedidoResponse> mapToResponse(List<Pedido> orders) {
        return orders.stream().map((o) -> {
            return this.mapToResponse(o);
        }).toList();
    }

    private PedidoResponse mapToResponse(Pedido order) {
        return PedidoDTOMapper.toOrderResponse(order);
    }

    @GetMapping({ "" })
    public List<PedidoResponse> getAllOrders() {
        List<Pedido> orders = this.listarTodosPedidosUseCase.listarTodos();
        return this.mapToResponse(orders);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PedidoCriadoResponse> criarPedido(@RequestBody PedidoRequest pedidoRequest) {
        Pedido pedidoCriado = this.criarPedidoUseCase.criar(pedidoRequest);

        String qrcode = null;
        try {
            qrcode = gerarQRCodePagamentoUseCase.gerar(pedidoCriado.getId(), pedidoCriado.getPrecoTotal());
        } catch (ErroAoGerarQRCodeException e) {
            log.error("Erro ao tentar gerar qrcode para pedido: " + pedidoCriado.getId(), e);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PedidoCriadoResponse(pedidoCriado.getNumeroPedido(), qrcode));
    }

    @GetMapping({ "/{numeroPedido}" })
    public PedidoResponse getOrderByNumeroPedido(@PathVariable Long numeroPedido) {
        Pedido pedido = this.buscarPedidoPorNumeroPedidoUseCase.buscar(numeroPedido)
            .orElseThrow(() -> new NaoEncontradoException("pedido não encontrado"));
        return this.mapToResponse(pedido);
    }

    @PutMapping({ "/{orderNumber}/status" })
    public PedidoResponse updateOrderState(
            @PathVariable Long orderNumber,
            @RequestParam(required = true) StatusPedido valor) {
        Pedido updateState = this.atualizarStatusPedidoUseCase.atualizar(orderNumber, valor);
        return this.mapToResponse(updateState);
    }

    @GetMapping({ "/statePayment/{orderNumber}" })
    public ResponseEntity<StatusPagamentoPedidoResponse> findStatusPayment(@PathVariable Long orderNumber) {
        Pedido orderPaymentStatus = this.buscarPedidoPorNumeroPedidoUseCase.buscar(orderNumber)
            .orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado com esse número " + orderNumber));
        return ResponseEntity.status(HttpStatus.OK).body(new StatusPagamentoPedidoResponse(
                orderPaymentStatus.getNumeroPedido(), orderPaymentStatus.getInformacoesPagamento().statusConfirmacaoPagamento()));
    }

    @GetMapping("/follow-up")
    public ResponseEntity<PedidoAgrupoadoResponse> findFollowUp() {
        List<Pedido> orders = this.listarTodosPedidosUseCase.listarTodosNaoFinalizados();
        return ResponseEntity.status(HttpStatus.OK).body(PedidoAgrupadoMapper.toPedidos(orders));
    }

    // WEBHOOK
    @Transactional
    @PostMapping("/{pedidoId}/pagamento")
    public ResponseEntity<Void> webhookPagamento(@RequestBody InformacoesPagamentosRequest request, @PathVariable Long pedidoId) {
        InformacoesPagamentosCommand command = new InformacoesPagamentosCommand(pedidoId, request.transacaoId(), request.valor(), request.statusPagamento());
        processarPagamentoPedidoUseCase.processar(command);
        return ResponseEntity.ok().build();
    }
    

}
