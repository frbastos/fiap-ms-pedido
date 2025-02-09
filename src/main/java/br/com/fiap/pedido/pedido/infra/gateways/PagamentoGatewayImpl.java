package br.com.fiap.pedido.pedido.infra.gateways;

import java.math.BigDecimal;

import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;
import br.com.fiap.pedido.pedido.infra.feignclient.PagamentoFeignClient;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeResponse;

public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoFeignClient pagamentoFeignClient;

    public PagamentoGatewayImpl(PagamentoFeignClient pagamentoFeignClient) {
        this.pagamentoFeignClient = pagamentoFeignClient;
    }

    @Override
    public String gerar(Long pedidoId, BigDecimal valor) {
        String callbackUrl = "http://localhost:8080/pedidos/" + pedidoId + "/pagamento";
        QRCodeResponse resposta = pagamentoFeignClient
                .gerarQrcode(new QRCodeRequest("Pedido ID: " + pedidoId, valor, callbackUrl));
        return resposta.qrcode();
    }

}
