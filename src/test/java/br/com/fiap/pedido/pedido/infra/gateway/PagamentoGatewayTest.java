package br.com.fiap.pedido.pedido.infra.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;
import br.com.fiap.pedido.pedido.infra.feignclient.PagamentoFeignClient;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeResponse;
import br.com.fiap.pedido.pedido.infra.gateways.PagamentoGatewayImpl;

class PagamentoGatewayTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PagamentoFeignClient pagamentoFeignClient;

    private PagamentoGateway pagamentoGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        pagamentoGateway = new PagamentoGatewayImpl(pagamentoFeignClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirGerarPagamento() {
        // Arrange
        Long pedidoId = 123L;
        BigDecimal valor = new BigDecimal("99.90");
        String expectedQRCode = "qr-code-123";
        String expectedCallbackUrl = "http://localhost:8080/pedidos/123/pagamento";
    
        QRCodeRequest expectedRequest = new QRCodeRequest("Pedido ID: " + pedidoId, valor, expectedCallbackUrl);
        QRCodeResponse mockResponse = new QRCodeResponse(expectedQRCode);
    
        when(pagamentoFeignClient.gerarQrcode(expectedRequest)).thenReturn(mockResponse);
    
        // Act
        String result = pagamentoGateway.gerar(pedidoId, valor);
    
        // Assert
        assertEquals(expectedQRCode, result);
        verify(pagamentoFeignClient).gerarQrcode(expectedRequest);
    }

}
