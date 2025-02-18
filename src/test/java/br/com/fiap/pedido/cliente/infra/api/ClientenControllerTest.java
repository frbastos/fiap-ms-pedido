package br.com.fiap.pedido.cliente.infra.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.fiap.pedido.cliente.application.usecase.BuscarClienteUseCase;
import br.com.fiap.pedido.cliente.application.usecase.RegistrarClienteUseCase;
import br.com.fiap.pedido.cliente.domain.entities.Cliente;
import br.com.fiap.pedido.cliente.infra.api.dto.ClienteRequest;
import br.com.fiap.pedido.shared.exception.ErrorHandlingControllerAdvice;
import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import br.com.fiap.shared.ClienteHelper;

class ClientenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarClienteUseCase registrarClienteUseCase;

    @Mock
    private BuscarClienteUseCase buscarClienteUseCase;

    private AutoCloseable autoCloseable;

    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        clienteController = new ClienteController(buscarClienteUseCase, registrarClienteUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirRegistrarCliente() throws Exception {

        ClienteRequest clienteRequest = new ClienteRequest("Fellipe Bastos", "fbastos95@gmail.com", "000.000.000-00");

        when(registrarClienteUseCase.save(any(Cliente.class)))
                .thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(
                post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequest)))
                .andExpect(status().isOk())
                // .andDo(print())
                .andExpect(jsonPath("$.nome").value(clienteRequest.nome()))
                .andExpect(jsonPath("$.documento").value(clienteRequest.documento()))
                .andExpect(jsonPath("$.email").value(clienteRequest.email()));

        verify(registrarClienteUseCase).save(any(Cliente.class));

    }

    @Test
    void devePermitirBuscarClientePorDoumento() throws Exception {

        String documento = "000.000.000-00";
        Cliente cliente = ClienteHelper.gerarObjeto();

        when(buscarClienteUseCase.buscarPorDocumento(documento))
                .thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes")
                .param("documento", documento)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()))
                .andExpect(jsonPath("$.documento").value(cliente.getDocumento()))
                .andExpect(jsonPath("$.email").value(cliente.getEmail()));

        verify(buscarClienteUseCase).buscarPorDocumento(anyString());

    }

    @Test
    void deveGerarExcecao_QuandoBuscarCliente_IdNaoEncontrado() throws Exception {

        when(buscarClienteUseCase.buscarPorDocumento(any(String.class)))
                .thenThrow(new NaoEncontradoException());

        mockMvc.perform(get("/clientes")
                .param("documento", "111.111.111-14")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(buscarClienteUseCase).buscarPorDocumento(any(String.class));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
