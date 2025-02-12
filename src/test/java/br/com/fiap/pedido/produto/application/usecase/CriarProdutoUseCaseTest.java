package br.com.fiap.pedido.produto.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.CriarProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.shared.ProdutoHelper;

class CriarProdutoUseCaseTest {

     @Mock
    private ProdutoGateway produtoGateway;

     private AutoCloseable autoCloseable;
     
         private CriarProdutoUseCaseImpl usecase;
     
         @BeforeEach
         void setUp(){
             autoCloseable = MockitoAnnotations.openMocks(this);
             usecase = new CriarProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void devePermitirCriarProduto(){
        Produto produto = ProdutoHelper.gerarXSalada();
        when(produtoGateway.salvar(produto))
            .thenAnswer(i -> i.getArgument(0));

        Produto produtoCriado = usecase.criar(produto);

        assertNotNull(produtoCriado);
        assertEquals(produto, produtoCriado);
    }

}
