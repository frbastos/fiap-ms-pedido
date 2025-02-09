package br.com.fiap.pedido.pedido.infra.feignclient.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoCozinhaRequest(LocalDateTime dataCriacao,
        Long numeroPedido,
        String cliente,
        List<ItemPedidoCozinhaRequest> itens) {

}
