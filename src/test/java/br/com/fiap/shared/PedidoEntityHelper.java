package br.com.fiap.shared;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;

import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.persistence.ItemPedidoEntity;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoEntity;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;

public class PedidoEntityHelper {

    public static PedidoEntity gerar(){
        Long id = geradorId();

        ItemPedidoEntity item1 = gerarItem();
        ItemPedidoEntity item2 = gerarItem();
        List<ItemPedidoEntity> itens = Arrays.asList(item1, item2);
        BigDecimal precoTotal = itens.stream().map(ItemPedidoEntity::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        PedidoEntity pedidoEntity = new PedidoEntity(id, null, itens, StatusPedido.PENDENTE, precoTotal, UUID.randomUUID().toString(), StatusConfirmacaoPagamento.APROVADO, LocalDateTime.now(), 1010L);
        itens.forEach(i -> i.setPedido(pedidoEntity));

        return pedidoEntity;
    }

    public static ItemPedidoEntity gerarItem(){
        ProdutoEntity produto = ProdutoEntiytHelper.gerar();
        return new ItemPedidoEntity(geradorId(), produto, produto.getPreco(), 1, null, produto.getPreco(), null);
    }

    private static Long geradorId(){
        return RandomGenerator.getDefault().nextLong();
    }

}
