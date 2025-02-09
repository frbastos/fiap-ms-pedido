package br.com.fiap.pedido.pedido.application.usecase;

import java.math.BigDecimal;

import br.com.fiap.pedido.pedido.application.exception.ErroAoGerarQRCodeException;
import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;

public class GerarQRCodePagamentoUseCaseImpl implements GerarQRCodePagamentoUseCase {

    private PagamentoGateway pagamentoGateway;

    public GerarQRCodePagamentoUseCaseImpl(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public String gerar(Long pedidoId, BigDecimal valor) throws ErroAoGerarQRCodeException{
        try {
            return pagamentoGateway.gerar(pedidoId, valor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroAoGerarQRCodeException("Erro ao gerar qrcode", e);
        }
    }

}
