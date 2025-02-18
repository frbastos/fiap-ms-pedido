package br.com.fiap.pedido.pedido.infra.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;
import br.com.fiap.pedido.pedido.infra.feignclient.PagamentoFeignClient;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeResponse;
import br.com.fiap.pedido.pedido.infra.gateways.PagamentoGatewayImpl;

class PagamentoGatewayTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PagamentoFeignClient pagamentoFeignClient;

    @Mock
    private Environment environment;

    private PagamentoGateway pagamentoGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        pagamentoGateway = new PagamentoGatewayImpl(pagamentoFeignClient, environment);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirGerarPagamento() {
        // Arrange
        String baseUrl = "http://localhost:8080";
        Long pedidoId = 123L;
        BigDecimal valor = new BigDecimal("99.90");
        String expectedQRCode = "qr-code-123";
        String expectedCallbackUrl = baseUrl + "/pedidos/123/pagamento";
    
        QRCodeRequest expectedRequest = new QRCodeRequest("Pedido ID: " + pedidoId, valor, expectedCallbackUrl);
        QRCodeResponse mockResponse = new QRCodeResponse(expectedQRCode);
    
        when(pagamentoFeignClient.gerarQrcode(expectedRequest)).thenReturn(mockResponse);
        when( environment.getProperty(anyString())).thenReturn(baseUrl);
    
        // Act
        String result = pagamentoGateway.gerar(pedidoId, valor);
    
        // Assert
        assertEquals(expectedQRCode, result);
        verify(pagamentoFeignClient).gerarQrcode(expectedRequest);
    }

}
