package br.com.fiap.pedido.produto.infra.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.produto.infra.exceptions.ProdutoEmUsoException;
import br.com.fiap.pedido.produto.infra.gateways.ProdutoRepositoryGateway;
import br.com.fiap.pedido.produto.infra.gateways.mappers.ProdutoEntityMapper;
import br.com.fiap.shared.ProdutoEntiytHelper;
import br.com.fiap.shared.ProdutoHelper;

class ProdutoRepositoryGatewayTest {

    private AutoCloseable autoCloseable;

    @Mock
    private ProdutoRepository repository;

    private ProdutoRepositoryGateway produtoRepositoryGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        produtoRepositoryGateway = new ProdutoRepositoryGateway(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirSalvarProduto() {
        // Arrange
        Produto produto = ProdutoHelper.gerarXSalada();
        ProdutoEntity produtoEntity = ProdutoEntityMapper.toEntity(produto);

        when(repository.save(any())).thenReturn(produtoEntity);

        // Act
        Produto result = produtoRepositoryGateway.salvar(produto);

        // Assert
        verify(repository).save(any(ProdutoEntity.class));
        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
    }

    @Test
    void devePermitirRemover() {

        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        produtoRepositoryGateway.remover(id);

        verify(repository).deleteById(any());
    }

    @Test
    void deveGerarExcecao_AoRemoverProduto_ProdutoEmUso() {

        Long id = 1L;
        doThrow(new DataIntegrityViolationException("Product in use"))
                .when(repository).deleteById(any());

        assertThrows(ProdutoEmUsoException.class, () -> {
            produtoRepositoryGateway.remover(id);
        });

        verify(repository).deleteById(any());
    }

    @Test
    void deveListarTodosProdutos() {

        ProdutoEntity produto1 = ProdutoEntiytHelper.gerar();
        ProdutoEntity produto2 = ProdutoEntiytHelper.gerar();

        when(repository.findAll())
                .thenReturn(Arrays.asList(produto1, produto2));

        List<Produto> resultado = produtoRepositoryGateway.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void deveBuscarPorId() {

        Long id = 1L;
        ProdutoEntity produtoEntity = ProdutoEntiytHelper.gerar();

        when(repository.findById(any()))
                .thenReturn(Optional.of(produtoEntity));

        Optional<Produto> resultado = produtoRepositoryGateway.buscarPorId(id);

        // Assert
        verify(repository).findById(any());
        assertThat(resultado)
                .isPresent()
                .get()
                .satisfies(p -> {
                    assertThat(p.getDescricao()).isEqualTo(produtoEntity.getDescricao());
                    assertThat(p.getPreco()).isEqualTo(produtoEntity.getPreco());
                    assertThat(p.getCategoria()).isEqualTo(produtoEntity.getCategoria());
                });
    }

    @Test
    void devePermitirBuscarCategoria() {

        Categoria categoria = Categoria.LANCHE;

        ProdutoEntity entity1 = ProdutoEntiytHelper.gerar();
        ProdutoEntity entity2 = ProdutoEntiytHelper.gerar();

        when(repository.findByCategoria(any()))
                .thenReturn(Arrays.asList(entity1, entity2));

        List<Produto> resultado = produtoRepositoryGateway.buscarPorCategoria(categoria);

        verify(repository).findByCategoria(any());
        assertThat(resultado)
                .satisfies(p -> {
                    assertThat(p.get(0).getDescricao()).isEqualTo(entity1.getDescricao());
                    assertThat(p.get(0).getPreco()).isEqualTo(entity1.getPreco());
                    assertThat(p.get(0).getCategoria()).isEqualTo(entity1.getCategoria());
                });

        assertThat(resultado)
                .satisfies(p -> {
                    assertThat(p.get(1).getDescricao()).isEqualTo(entity2.getDescricao());
                    assertThat(p.get(1).getPreco()).isEqualTo(entity2.getPreco());
                    assertThat(p.get(1).getCategoria()).isEqualTo(entity2.getCategoria());
                });
    }

}
