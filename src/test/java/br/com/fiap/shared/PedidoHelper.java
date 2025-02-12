package br.com.fiap.shared;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.pedido.domain.entities.ItemPedido;
import br.com.fiap.pedido.pedido.domain.entities.Pedido;
import br.com.fiap.pedido.pedido.domain.valueobjects.InformacoesPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusConfirmacaoPagamento;
import br.com.fiap.pedido.pedido.domain.valueobjects.StatusPedido;
import br.com.fiap.pedido.pedido.infra.api.dto.request.ItemPedidoRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;

public class PedidoHelper {

        public static Pedido gerar() {

                List<ItemPedido> itens = new ArrayList<>();
                itens.add(new ItemPedido(null, ProdutoHelper.gerarXSalada(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarBatataFrita(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarCocaCola(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarPudim(), 1, null));

                InformacoesPagamento informacoesPagamento = new InformacoesPagamento(UUID.randomUUID().toString(),
                                StatusConfirmacaoPagamento.APROVADO);

                return new Pedido(
                                null,
                                null,
                                itens,
                                StatusPedido.PENDENTE,
                                LocalDateTime.now(),
                                1010L,
                                informacoesPagamento);
        }

        public static Pedido gerar(Long id, StatusPedido status) {

                List<ItemPedido> itens = new ArrayList<>();
                itens.add(new ItemPedido(null, ProdutoHelper.gerarXSalada(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarBatataFrita(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarCocaCola(), 1, null));
                itens.add(new ItemPedido(null, ProdutoHelper.gerarPudim(), 1, null));

                InformacoesPagamento informacoesPagamento = new InformacoesPagamento(UUID.randomUUID().toString(),
                                StatusConfirmacaoPagamento.APROVADO);

                return new Pedido(
                                id,
                                null,
                                itens,
                                status,
                                LocalDateTime.now(),
                                1010L,
                                informacoesPagamento);
        }

        public static PedidoRequest gerarRequest() {
                // Arrange
                Cliente cliente = ClienteHelper.gerarObjeto();
                Produto xSalada = ProdutoHelper.gerarXSalada();
                Produto cocaCola = ProdutoHelper.gerarXSalada();

                ItemPedidoRequest item1 = new ItemPedidoRequest(xSalada.getId(), 1, null);
                ItemPedidoRequest item2 = new ItemPedidoRequest(cocaCola.getId(), 1, null);

                return new PedidoRequest(cliente.getDocumento(), Arrays.asList(item1, item2));
        }

        public static Pedido gerar(PedidoRequest request) {

                List<ItemPedido> itensPedido = new ArrayList<>();

                for (var i = 0; i < request.itens().size(); i++) {
                        Long indice = i + 1L;
                        Produto produto = new Produto("Produto " + indice, BigDecimal.valueOf(10), Categoria.LANCHE);
                        ItemPedido itemPedido = new ItemPedido(indice, produto, 1, null);
                        itensPedido.add(itemPedido);
                }

                InformacoesPagamento informacoesPagamento = new InformacoesPagamento(UUID.randomUUID().toString(),
                                StatusConfirmacaoPagamento.APROVADO);

                return new Pedido(1L, null, itensPedido, StatusPedido.PENDENTE, LocalDateTime.now(), 1010L,
                                informacoesPagamento);

        }

}
