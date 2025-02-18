
# FIAP - Arquitetura de Software -> Sistema de Pedido

## Descrição do Projeto

Este projeto é um sistema de pedidos desenvolvido em **Java Spring Boot**, permitindo que clientes façam pedidos personalizados, realizem pagamentos via QRCode do **Mercado Pago**, acompanhem o status de seus pedidos e recebam notificações quando o pedido estiver pronto. O sistema também oferece gerenciamento de clientes, produtos e categorias, além de um painel administrativo para monitoramento de pedidos.

## Funcionalidades

### Pedido
- **Identificação do Cliente**: Clientes podem se identificar via CPF, nome e e-mail, ou optar por fazer pedidos anonimamente.
- **Montagem de Combo**: Clientes podem montar seus combos escolhendo:
    - Lanche
    - Acompanhamento
    - Bebida
    - Sobremesa
- Exibição de nome, descrição e preço dos produtos em cada etapa.

### Pagamento
- **QRCode Mercado Pago**: Pagamento integrado via QRCode.

### Acompanhamento
- **Status do Pedido**: Monitoramento em tempo real dos estados do pedido:
    - Recebido
    - Em preparação
    - Pronto
    - Finalizado

### Entrega
- **Notificação**: Notificação ao cliente quando o pedido estiver pronto para retirada.

### Gerenciamento
- **Clientes**: Gestão de clientes para campanhas promocionais.
- **Produtos e Categorias**: Gerenciamento de produtos com nome, categoria, preço, descrição e imagens.
- **Pedidos**: Acompanhamento de pedidos em andamento e tempo de espera.

## Microserviço de Pedidos

O microserviço de pedidos gerencia todo o fluxo de pedidos realizados pelos clientes, garantindo que sejam registrados corretamente, processados e encaminhados para a cozinha. Além disso, gerencia clientes e produtos do menu.

### Principais Funcionalidades

**Recebimento de Pedidos**
- Registra pedidos no banco de dados (utilizando Postman para testes).
- Permite que clientes montem seus combos escolhendo lanche, acompanhamento, bebida e sobremesa.
- Gera um identificador único para cada pedido.

**Integração com o Microserviço de Pagamento**
- Envia requisição ao Microserviço de Pagamento
solicitando um QR Code do Mercado Pago para pagamento.
- Aguarda confirmação do pagamento via webhook.
- Atualiza o status do pedido após a confirmação do pagamento.

**Integração com o Microserviço da Cozinha**
- Envia pedidos confirmados para a cozinha.
- Recebe atualizações de status da cozinha (Em preparação, Pronto).
- Permite que a cozinha altere o status do pedido diretamente.

**Gerenciamento de Clientes e Produtos**
- Cadastra e gerencia clientes (para campanhas promocionais e identificação no pedido).
- Administra os produtos disponíveis no menu da lanchonete, incluindo nome, categoria, preço, descrição e imagens.

**Fluxo de Comunicação**
1. O cliente faz um pedido e o microserviço de pedidos registra no banco de dados.
2. O microserviço solicita um QR Code para pagamento ao Microserviço de Pagamento.
3. Quando o pagamento for confirmado via webhook, o status do pedido é atualizado.
4. O pedido é enviado ao Microserviço da Cozinha, onde o status de preparo pode ser atualizado.
5. Quando o pedido estiver pronto, o status é atualizado e o cliente pode retirá-lo.

**Comunicação com outros microserviços**
- **Pagamento**: Solicita um QR Code e recebe a confirmação do pagamento via webhook.
- **Cozinha**: Notifica a cozinha sobre novos pedidos e recebe atualizações de status.

### Passos para Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/frbastos/fiap-ms-pedido
   cd fiap-ms-pedido
   ```

2. Executar testes unitário:
    ```
        mvn test -Punit-test
    ```

3. Executar testes de sistema (BDD):
    ```
        mvn test -Psystem-test
    ```

4. Executar projeto com spring boot:
    ```
        mvn spring-boot:run
    ```

## Documentação Complementar

- [Notion do Projeto](https://global-gorilla-13f.notion.site/FIAP-Projeto-Lanchonete-26bfdcca5de84ce8974cbfad8286dcc2)
- [Miro com Fluxos](https://miro.com/app/board/uXjVK3DvRAo=/?share_link_id=212036327976)
- [Collection Postman](https://drive.google.com/file/d/10MDxhCituEKv-HloQL27nnfv5chSEFAN/view?usp=sharing)

## Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).