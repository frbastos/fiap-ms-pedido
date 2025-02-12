package br.com.fiap.pedido.produto.infra.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.pedido.produto.application.AtualizarProdutoUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutosPorCategoriaUseCase;
import br.com.fiap.pedido.produto.application.CriarProdutoUseCase;
import br.com.fiap.pedido.produto.application.ListarTodosProdutosUseCase;
import br.com.fiap.pedido.produto.application.RemoverProdutoUseCase;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.produto.infra.api.dto.AtualizaProdutoRequest;
import br.com.fiap.pedido.produto.infra.api.dto.CriaProdutoRequest;
import br.com.fiap.pedido.produto.infra.api.dto.ProdutoCommandMapper;
import br.com.fiap.pedido.shared.exception.ErrorHandlingControllerAdvice;
import br.com.fiap.shared.ProdutoHelper;

public class ProdutoControllerTest {

    private MockMvc mockMvc;

    private AutoCloseable autoCloseable;

    @Mock
    private ProdutoCommandMapper productCommandMapper;

    @Mock
    private CriarProdutoUseCase createProductUseCase;

    @Mock
    private BuscarProdutoPorIdUseCase getProductByIdUseCase;

    @Mock
    private AtualizarProdutoUseCase updateProductUseCase;

    @Mock
    private BuscarProdutosPorCategoriaUseCase getProductsByCategoryUseCase;

    @Mock
    private ListarTodosProdutosUseCase getAllProductsUseCase;

    @Mock
    private RemoverProdutoUseCase removeProductUseCase;

    private ProductsController productsController;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productsController = new ProductsController(
                productCommandMapper,
                createProductUseCase,
                getProductByIdUseCase,
                updateProductUseCase,
                getProductsByCategoryUseCase,
                getAllProductsUseCase,
                removeProductUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(productsController)
                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePemitirCriarProduto() throws Exception {
        CriaProdutoRequest request = new CriaProdutoRequest("Produto 1", BigDecimal.valueOf(15), Categoria.LANCHE);

        when(createProductUseCase.criar(any()))
                .thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(
                post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value(request.descricao()))
                .andExpect(jsonPath("$.preco").value(request.preco()))
                .andExpect(jsonPath("$.categoria").value(request.categoria().toString()));

        verify(createProductUseCase).criar(any());
    }

    @Test
    void devePermitirAtualizarProduto() throws Exception {
        Long id = 2L;
        Produto bataFrita = ProdutoHelper.gerarBatataFrita();
        AtualizaProdutoRequest request = new AtualizaProdutoRequest("Produto 1", BigDecimal.valueOf(15),
                Categoria.LANCHE);

        when(updateProductUseCase.atualizar(any()))
                .thenReturn(bataFrita);

        mockMvc.perform(
                put("/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value(bataFrita.getDescricao()))
                .andExpect(jsonPath("$.preco").value(bataFrita.getPreco()))
                .andExpect(jsonPath("$.categoria").value(bataFrita.getCategoria().toString()));

        verify(updateProductUseCase).atualizar(any());

    }

    @Test
    void devePermitirBuscarProdutoPorId() throws Exception {

        Produto bataFrita = ProdutoHelper.gerarBatataFrita();

        when(getProductByIdUseCase.buscarPorId(anyLong()))
                .thenReturn(Optional.of(bataFrita));

        mockMvc.perform(
                get("/produtos/{id}", bataFrita.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value(bataFrita.getDescricao()))
                .andExpect(jsonPath("$.preco").value(bataFrita.getPreco()))
                .andExpect(jsonPath("$.categoria").value(bataFrita.getCategoria().toString()));

    }

    @Test
    void devePermitirListarTodosProdutosComCategoria() throws Exception {

        Produto xSalada = ProdutoHelper.gerarXSalada();

        when(getProductsByCategoryUseCase.buscarPorCategoria(any()))
                .thenReturn(Arrays.asList(xSalada));

        mockMvc.perform(
                get("/produtos")
                        .param("category", Categoria.LANCHE.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value(xSalada.getDescricao()))
                .andExpect(jsonPath("$[0].preco").value(xSalada.getPreco()))
                .andExpect(jsonPath("$[0].categoria").value(xSalada.getCategoria().toString()));

        verify(getProductsByCategoryUseCase).buscarPorCategoria(any());
    }

    @Test
    void devePermitirListarTodosProdutosSemCategoria() throws Exception {

        Produto xSalada = ProdutoHelper.gerarXSalada();

        when(getAllProductsUseCase.listarTodos())
                .thenReturn(Arrays.asList(xSalada));

        mockMvc.perform(
                get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value(xSalada.getDescricao()))
                .andExpect(jsonPath("$[0].preco").value(xSalada.getPreco()))
                .andExpect(jsonPath("$[0].categoria").value(xSalada.getCategoria().toString()));

        verify(getAllProductsUseCase).listarTodos();
    }

    @Test
    void devePermitirRemoverProduto() throws Exception {

        Long id = 1L;

        doNothing().when(removeProductUseCase).remover(any());

        mockMvc.perform(
                delete("/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(removeProductUseCase).remover(anyLong());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
