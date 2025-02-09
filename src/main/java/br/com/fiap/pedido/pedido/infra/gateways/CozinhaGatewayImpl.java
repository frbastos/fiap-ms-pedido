package br.com.fiap.pedido.pedido.infra.gateways;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.fiap.pedido.pedido.application.usecase.CozinhaGateway;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.infra.feignclient.ProducaoFeignClient;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.ItemPedidoCozinhaRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.PedidoCozinhaRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CozinhaGatewayImpl implements CozinhaGateway {

    private final ProducaoFeignClient producaoFeignClient;

    @Override
    public void registrarPedido(Pedido pedido) {
        var nomeCliente = pedido.getCliente() != null ? pedido.getCliente().getNome() : null;
        var itens = pedido.getItens().stream().map(
                i -> new ItemPedidoCozinhaRequest(i.getProduto().getDescricao(), i.getQuantidade(), i.getObservacao()))
                .toList();

        var pedidoCozinhaRequest = new PedidoCozinhaRequest(LocalDateTime.now(), pedido.getNumeroPedido(), nomeCliente, itens);
        producaoFeignClient.registrarNovoPedido(pedidoCozinhaRequest);
    }

}
