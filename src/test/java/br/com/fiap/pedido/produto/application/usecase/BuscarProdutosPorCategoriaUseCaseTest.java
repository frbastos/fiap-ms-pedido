package br.com.fiap.pedido.produto.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.BuscarProdutosPorCategoriaUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.shared.ProdutoHelper;

class BuscarProdutosPorCategoriaUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    private AutoCloseable autoCloseable;

    private BuscarProdutosPorCategoriaUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new BuscarProdutosPorCategoriaUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirBuscarProdutoPorCategoria() {
        Produto xSalada = ProdutoHelper.gerarXSalada();

        when(produtoGateway.buscarPorCategoria(any())).thenReturn(Arrays.asList(xSalada));

        List<Produto> result = usecase.buscarPorCategoria(Categoria.LANCHE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(xSalada, result.get(0));
    }
}
