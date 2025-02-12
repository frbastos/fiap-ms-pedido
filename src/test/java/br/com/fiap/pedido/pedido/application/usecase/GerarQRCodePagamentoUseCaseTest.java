package br.com.fiap.pedido.pedido.application.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.pedido.pedido.application.exception.ErroAoGerarQRCodeException;
import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;

class GerarQRCodePagamentoUseCaseTest {

    private AutoCloseable autoCloseable;

    @Mock
    private PagamentoGateway gateway;

    private GerarQRCodePagamentoUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usecase = new GerarQRCodePagamentoUseCaseImpl(gateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirGerarQRCode() {
        Long pedidoId = 10L;
        BigDecimal valor = BigDecimal.valueOf(150);

        when(gateway.gerar(any(Long.class), any(BigDecimal.class)))
                .thenReturn(UUID.randomUUID().toString());

        String resultado = usecase.gerar(pedidoId, valor);

        assertNotNull(resultado);
        verify(gateway).gerar(pedidoId, valor);
    }

    @Test
    void deveGerarExcecao_AoGerarQRCode() {
        Long pedidoId = 10L;
        BigDecimal valor = BigDecimal.valueOf(150);

        when(gateway.gerar(any(Long.class), any(BigDecimal.class)))
            .thenThrow(new ErroAoGerarQRCodeException());

        assertThatThrownBy(() -> usecase.gerar(pedidoId, valor))
            .isInstanceOf(ErroAoGerarQRCodeException.class);

        verify(gateway).gerar(pedidoId, valor);
    }

}
