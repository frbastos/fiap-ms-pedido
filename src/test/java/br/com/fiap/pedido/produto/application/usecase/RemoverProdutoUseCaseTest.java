package br.com.fiap.pedido.produto.application.usecase;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.produto.application.RemoverProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.shared.ProdutoHelper;

class RemoverProdutoUseCaseTest {

     @Mock
    private ProdutoGateway produtoGateway;

    private AutoCloseable autoCloseable;
    
        private RemoverProdutoUseCaseImpl usecase;
    
        @BeforeEach
        void setUp(){
            autoCloseable = MockitoAnnotations.openMocks(this);
            usecase = new RemoverProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void deveRemoverProduto(){

        Produto produto = ProdutoHelper.gerarXSalada();
        Long produtoId = produto.getId();

        when(produtoGateway.buscarPorId(produtoId))
            .thenReturn(Optional.of(produto));

        doNothing().when(produtoGateway).remover(produtoId);

        usecase.remover(produtoId);

        verify(produtoGateway).remover(produtoId);
    }

}
