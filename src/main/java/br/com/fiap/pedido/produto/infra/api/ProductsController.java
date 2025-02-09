package br.com.fiap.pedido.produto.infra.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.pedido.produto.application.AtualizarProdutoUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutosPorCategoriaUseCase;
import br.com.fiap.pedido.produto.application.CriarProdutoUseCase;
import br.com.fiap.pedido.produto.application.ListarTodosProdutosUseCase;
import br.com.fiap.pedido.produto.application.RemoverProdutoUseCase;
import br.com.fiap.pedido.produto.application.commands.AtualizaProdutoCommand;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.produto.infra.api.dto.AtualizaProdutoRequest;
import br.com.fiap.pedido.produto.infra.api.dto.CriaProdutoRequest;
import br.com.fiap.pedido.produto.infra.api.dto.ProdutoCommandMapper;
import br.com.fiap.pedido.produto.infra.api.dto.ProdutoDTOMapper;
import br.com.fiap.pedido.produto.infra.api.dto.ProdutoResponse;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProductsController {

    private final ProdutoDTOMapper productDTOMapper;
    private final ProdutoCommandMapper productCommandMapper;
    private final CriarProdutoUseCase createProductUseCase;
    private final BuscarProdutoPorIdUseCase getProductByIdUseCase;
    private final AtualizarProdutoUseCase updateProductUseCase;
    private final BuscarProdutosPorCategoriaUseCase getProductsByCategoryUseCase;
    private final ListarTodosProdutosUseCase getAllProductsUseCase;
    private final RemoverProdutoUseCase removeProductUseCase;
    

    @PostMapping("")
    public ProdutoResponse criarProduto(@RequestBody CriaProdutoRequest request) {
        Produto productObjDomain = productDTOMapper.toProduct(request);
        Produto product = createProductUseCase.criar(productObjDomain);
        return productDTOMapper.toResponse(product);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizarProduto(@PathVariable("id") Long id, @RequestBody AtualizaProdutoRequest update) {
        AtualizaProdutoCommand updateCommand = productCommandMapper.toAtualizaProdutoCommand(id, update);
        Produto product = updateProductUseCase.atualizar(updateCommand);
        return productDTOMapper.toResponse(product);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscarProdutoPorId(@PathVariable("id") Long id) {
        Produto product = this.getProductByIdUseCase.buscarPorId(id).orElseThrow(NaoEncontradoException::new);
        return productDTOMapper.toResponse(product);
    }

    @GetMapping("")
    public List<ProdutoResponse> listarTodosProdutos(@RequestParam(value = "category", required = false) Categoria category) {
        List<Produto> products;
        if (category != null) {
            products = getProductsByCategoryUseCase.buscarPorCategoria(category);
        }else{
            products = getAllProductsUseCase.listarTodos();
        }
        return products.stream().map(productDTOMapper::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable("id") Long id) {
        removeProductUseCase.remover(id);
        return ResponseEntity.ok().build();
    }

}
