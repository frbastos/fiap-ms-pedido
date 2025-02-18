package br.com.fiap.pedido.pedido.infra.gateways;

import java.math.BigDecimal;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.fiap.pedido.pedido.application.gateways.PagamentoGateway;
import br.com.fiap.pedido.pedido.infra.feignclient.PagamentoFeignClient;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoFeignClient pagamentoFeignClient;
    private final Environment environment;

    @Override
    public String gerar(Long pedidoId, BigDecimal valor) {
        String callbackUrl = environment.getProperty("app.callback.url") + "/pedidos/" + pedidoId + "/pagamento";
        QRCodeResponse resposta = pagamentoFeignClient
                .gerarQrcode(new QRCodeRequest("Pedido ID: " + pedidoId, valor, callbackUrl));
        return resposta.qrcode();
    }

}
