package br.com.fiap.pedido.cliente.infra.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.cliente.application.usecase.RegistrarClienteUseCase;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.api.dto.ClienteRequest;
import br.com.fiap.pedido.cliente.infra.api.dto.ClienteResponse;
import br.com.fiap.pedido.cliente.infra.mapper.ClienteDTOMapper;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final BuscarClienteUseCase clienteService;
    private final RegistrarClienteUseCase registrarCustomerUseCase;

    @PostMapping("")
    public ClienteResponse saveCustomer(@Valid @RequestBody ClienteRequest request) {
        Cliente customerObjDomain = ClienteDTOMapper.toCliente(request);
        Cliente customer = this.registrarCustomerUseCase.save(customerObjDomain);
        return ClienteDTOMapper.toResponse(customer);
    }

    @GetMapping("")
    public ClienteResponse searchCustomer(@RequestParam(value = "documento", required = false) String documento) {
        Cliente customer = this.clienteService.buscarPorDocumento(documento).orElseThrow(NaoEncontradoException::new);
        return ClienteDTOMapper.toResponse(customer);
    }

}
