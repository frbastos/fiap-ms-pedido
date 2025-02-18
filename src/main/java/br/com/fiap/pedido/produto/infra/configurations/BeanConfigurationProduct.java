package br.com.fiap.pedido.produto.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.pedido.produto.application.AtualizarProdutoUseCase;
import br.com.fiap.pedido.produto.application.AtualizarProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCaseImpl;
import br.com.fiap.pedido.produto.application.BuscarProdutosPorCategoriaUseCase;
import br.com.fiap.pedido.produto.application.BuscarProdutosPorCategoriaUseCaseImpl;
import br.com.fiap.pedido.produto.application.CriarProdutoUseCase;
import br.com.fiap.pedido.produto.application.CriarProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.ListarTodosProdutosUseCase;
import br.com.fiap.pedido.produto.application.ListarTodosProdutosUseCaseImpl;
import br.com.fiap.pedido.produto.application.RemoverProdutoUseCase;
import br.com.fiap.pedido.produto.application.RemoverProdutoUseCaseImpl;
import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.infra.api.dto.ProdutoCommandMapper;
import br.com.fiap.pedido.produto.infra.gateways.ProdutoRepositoryGateway;
import br.com.fiap.pedido.produto.infra.gateways.mappers.ProdutoEntityMapper;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoRepository;

@Configuration
public class BeanConfigurationProduct {

    @Bean
    ProdutoGateway produtoGateway(ProdutoRepository productRepository) {
        return new ProdutoRepositoryGateway(productRepository);
    }

    @Bean
    CriarProdutoUseCase createProductUseCase(ProdutoGateway produtoGateway) {
        return new CriarProdutoUseCaseImpl(produtoGateway);
    }

    @Bean
    ListarTodosProdutosUseCase getAllProductsUseCase(ProdutoGateway produtoGateway) {
        return new ListarTodosProdutosUseCaseImpl(produtoGateway);
    }

    @Bean
    BuscarProdutoPorIdUseCase getProductByIdUseCase(ProdutoGateway produtoGateway) {
        return new BuscarProdutoPorIdUseCaseImpl(produtoGateway);
    }

    @Bean
    BuscarProdutosPorCategoriaUseCase getProductsByCategoryUseCase(ProdutoGateway produtoGateway) {
        return new BuscarProdutosPorCategoriaUseCaseImpl(produtoGateway);
    }

    @Bean
    RemoverProdutoUseCase removeProductUseCase(ProdutoGateway produtoGateway) {
        return new RemoverProdutoUseCaseImpl(produtoGateway);
    }

    @Bean
    AtualizarProdutoUseCase updateProductUseCase(ProdutoGateway produtoGateway) {
        return new AtualizarProdutoUseCaseImpl(produtoGateway);
    }

    @Bean
    ProdutoEntityMapper productEntityMapper() {
        return new ProdutoEntityMapper();
    }

    @Bean
    ProdutoCommandMapper productCommandMapper() {
        return new ProdutoCommandMapper();
    }

}
