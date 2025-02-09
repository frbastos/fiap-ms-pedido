package br.com.fiap.pedido.produto.infra.gateways;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.fiap.pedido.produto.application.gateway.ProdutoGateway;
import br.com.fiap.pedido.produto.doimain.entities.Produto;
import br.com.fiap.pedido.produto.doimain.valueobjects.Categoria;
import br.com.fiap.pedido.produto.infra.exceptions.ProdutoEmUsoException;
import br.com.fiap.pedido.produto.infra.gateways.mappers.ProdutoEntityMapper;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoEntity;
import br.com.fiap.pedido.produto.infra.persistence.ProdutoRepository;

public class ProdutoRepositoryGateway implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;

    public ProdutoRepositoryGateway(
        ProdutoRepository produtoRepository) {

        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto product) {
        ProdutoEntity productEntity = ProdutoEntityMapper.toEntity(product);
        productEntity = this.produtoRepository.save(productEntity);
        return ProdutoEntityMapper.toObject(productEntity);
    }

    @Override
    public void remover(Long id) throws ProdutoEmUsoException{
        try {
            this.produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ProdutoEmUsoException();
        }
    }

    @Override
    public List<Produto> listarTodos() {
        List<ProdutoEntity> products = this.produtoRepository.findAll();
        return ProdutoEntityMapper.toListaProdutos(products);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return this.produtoRepository.findById(id).map(ProdutoEntityMapper::toObject);
    }

    @Override
    public List<Produto> buscarPorCategoria(Categoria category) {
        List<ProdutoEntity> byCategory = this.produtoRepository.findByCategoria(category);
        return ProdutoEntityMapper.toListaProdutos(byCategory);
    }
}
