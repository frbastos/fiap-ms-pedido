package br.com.fiap.pedido.produto.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.ListarTodosProdutosUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.shared.ProdutoHelper;

class ListarTodosProdutosUseCaseTest {

     @Mock
    private ProdutoGateway produtoGateway;

    private AutoCloseable autoCloseable;
    
        private ListarTodosProdutosUseCaseImpl usecase;
    
        @BeforeEach
        void setUp(){
            autoCloseable = MockitoAnnotations.openMocks(this);
            usecase = new ListarTodosProdutosUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void devePermitirListarProdutos(){
        Produto xSalada = ProdutoHelper.gerarXSalada();
        Produto cocaCola = ProdutoHelper.gerarCocaCola();
        when(produtoGateway.listarTodos())
            .thenReturn(Arrays.asList(xSalada, cocaCola));

        List<Produto> resultado = usecase.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(xSalada, resultado.get(0));
        assertEquals(cocaCola, resultado.get(1));
    }

}
