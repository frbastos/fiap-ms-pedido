package br.com.fiap.pedido.cliente.infra.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.pedido.cliente.application.gateway.ClienteGateway;
import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCaseImpl;
import br.com.fiap.pedido.cliente.application.usecase.RegistrarClienteUseCase;
import br.com.fiap.pedido.cliente.application.usecase.RegistrarClienteUseCaseImpl;
import br.com.fiap.pedido.cliente.infra.api.gateway.ClienteRepositoryGateway;
import br.com.fiap.pedido.cliente.infra.api.persistence.ClienteRepository;

@Configuration
public class BeanConfigurationCustomer {

    @Bean
    ClienteGateway customerGateway(ClienteRepository customerRepository) {
        return new ClienteRepositoryGateway(customerRepository);
    }

    @Bean
    BuscarClienteUseCase findCustomerUseCase(ClienteGateway customerGateway) {
        return new BuscarClienteUseCaseImpl(customerGateway);
    }

    @Bean
    RegistrarClienteUseCase registerCustomerUseCase(ClienteGateway customerGateway) {
        return new RegistrarClienteUseCaseImpl(customerGateway);
    }


}
