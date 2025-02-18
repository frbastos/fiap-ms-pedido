package br.com.fiap.pedido.pedido.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;
import br.com.fiap.pedido.pedido.application.gateways.PedidoGateway;
import br.com.fiap.pedido.pedido.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.AtualizarStatusPedidoUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorIdUseCase;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorIdUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorNumeroPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.BuscarPedidoPorNumeroPedidoUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.CozinhaGateway;
import br.com.fiap.pedido.pedido.application.usecase.CriarPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.CriarPedidoUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.EnviarPedidoCozinhaUseCase;
import br.com.fiap.pedido.pedido.application.usecase.EnviarPedidoCozinhaUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.GerarQRCodePagamentoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.GerarQRCodePagamentoUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.ListarTodosPedidosUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ListarTodosPedidosUseCaseImpl;
import br.com.fiap.pedido.pedido.application.usecase.ProcessarPagamentoPedidoUseCase;
import br.com.fiap.pedido.pedido.application.usecase.ProcessarPagamentoPedidoUseCaseImpl;
import br.com.fiap.pedido.pedido.infra.gateways.PedidoRepositoryGateway;
import br.com.fiap.pedido.pedido.infra.persistence.PedidoRepository;
import br.com.fiap.pedido.produto.application.BuscarProdutoPorIdUseCase;
import jakarta.persistence.EntityManager;

@Configuration
public class BeanConfigurationOrders {

    @Bean
    PedidoGateway orderGateway(PedidoRepository ordersRepository,
            EntityManager entityManager) {
        return new PedidoRepositoryGateway(ordersRepository, entityManager);
    }

    @Bean
    CriarPedidoUseCase createOrderUseCase(
            PedidoGateway orderGateway,
            BuscarClienteUseCase getProductByIdUseCase,
            BuscarProdutoPorIdUseCase findCustomerUseCase) {
        return new CriarPedidoUseCaseImpl(orderGateway, getProductByIdUseCase,
                findCustomerUseCase);
    }

    @Bean
    ListarTodosPedidosUseCase getAllOrdersUseCase(
            PedidoGateway orderGateway) {
        return new ListarTodosPedidosUseCaseImpl(orderGateway);
    }

    @Bean
    BuscarPedidoPorIdUseCase getOrderByIdUseCase(
            PedidoGateway orderGateway) {
        return new BuscarPedidoPorIdUseCaseImpl(orderGateway);
    }

    @Bean
    AtualizarStatusPedidoUseCase updateOrderStateUseCase(
            PedidoGateway orderGateway) {
        return new AtualizarStatusPedidoUseCaseImpl(orderGateway);
    }

    @Bean
    BuscarPedidoPorNumeroPedidoUseCase getOrderByOrderNumber(PedidoGateway orderGateway) {
        return new BuscarPedidoPorNumeroPedidoUseCaseImpl(orderGateway);
    }

    @Bean
    GerarQRCodePagamentoUseCase gerarQRCodePagamentoUseCase(PagamentoGateway pagamentoGateway){
        return new GerarQRCodePagamentoUseCaseImpl(pagamentoGateway);
    }

    @Bean
    ProcessarPagamentoPedidoUseCase processarPagamentoPedidoUseCase(PedidoGateway pedidoGateway, BuscarPedidoPorIdUseCase buscarClienteUseCase, EnviarPedidoCozinhaUseCase enviarPedidoCozinhaUseCase){
        return new ProcessarPagamentoPedidoUseCaseImpl(pedidoGateway, buscarClienteUseCase, enviarPedidoCozinhaUseCase);
    }

    @Bean
    EnviarPedidoCozinhaUseCase enviarPedidoCozinhaUseCase(CozinhaGateway cozinhaGateway){
        return new EnviarPedidoCozinhaUseCaseImpl(cozinhaGateway);
    }

}
