Feature: Pedido

    Scenario: Registrar pedido
        When registrar um novo pedido
        Then o pedido é registrado com sucesso

    Scenario: Buscar pedido existente
        Given que um pedido já foi criado
        When requisitar a buscar do pedido
        Then o pedido é exibido com sucesso

    Scenario: Follow up de pedidos
        Given que um pedido já foi criado
        When requisitar o acompanhamentos dos pedidos
        Then os pedidos devem ser apresentados

    Scenario: Processar pagamento
        Given que um pedido já foi criado
        When requisitar a buscar do pedido
        Then apos 10 segundos o pagamento é processado