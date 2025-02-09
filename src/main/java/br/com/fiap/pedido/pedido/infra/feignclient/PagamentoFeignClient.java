package br.com.fiap.pedido.pedido.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeRequest;
import br.com.fiap.pedido.pedido.infra.feignclient.dto.QRCodeResponse;

@FeignClient(name = "pagamentosClient", url = "http://localhost:8081")
public interface PagamentoFeignClient{

    @PostMapping("/pagamentos/qrcode")
    QRCodeResponse gerarQrcode(@RequestBody QRCodeRequest request);

}
