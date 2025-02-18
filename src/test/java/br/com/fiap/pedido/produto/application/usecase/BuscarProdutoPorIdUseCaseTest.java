package br.com.fiap.pedido.produto.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.shared.ProdutoHelper;

class BuscarProdutoPorIdUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private ProdutoGateway produtoGateway;

    private BuscarProdutoPorIdUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new BuscarProdutoPorIdUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirBuscarPedidoPorId() {
        Produto produto = ProdutoHelper.gerarXSalada();

        when(produtoGateway.buscarPorId(anyLong())).thenReturn(Optional.of(produto));

        Optional<Produto> result = usecase.buscarPorId(anyLong());

        assertTrue(result.isPresent());
        assertEquals(produto, result.get());
    }
}
