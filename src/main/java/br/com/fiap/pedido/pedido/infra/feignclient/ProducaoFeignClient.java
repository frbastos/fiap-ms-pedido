package br.com.fiap.pedido.pedido.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.fiap.pedido.pedido.infra.feignclient.dto.PedidoCozinhaRequest;
import jakarta.validation.Valid;

@FeignClient(name = "producaoClient", url = "${producao.service.url}")
public interface ProducaoFeignClient {

    @PostMapping("/cozinha/pedido")
    public ResponseEntity<Void> registrarNovoPedido(@Valid @RequestBody PedidoCozinhaRequest request);

}
