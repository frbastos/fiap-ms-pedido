package br.com.fiap.pedido.produto.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.AtualizarProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.shared.ProdutoHelper;

class AtualizarProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    private AutoCloseable autoCloseable;

    private AtualizarProdutoUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new AtualizarProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePemitirAtualizarProduto() {

        Produto produto = ProdutoHelper.gerarXSalada();
        AtualizaProdutoCommand comando = new AtualizaProdutoCommand(produto.getId(), produto.getDescricao(),
                produto.getPreco(), produto.getCategoria());

        when(produtoGateway.buscarPorId(any(Long.class)))
                .thenReturn(Optional.of(produto));

        when(produtoGateway.salvar(produto))
                .thenReturn(produto);

        usecase.atualizar(comando);

        assertNotNull(produto);
        verify(produtoGateway).buscarPorId(any());
        verify(produtoGateway).salvar(any());

    }
}
