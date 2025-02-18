package br.com.fiap.pedido.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import br.com.fiap.pedido.pedido.infra.api.dto.request.PedidoRequest;
import br.com.fiap.pedido.pedido.infra.api.dto.response.PedidoCriadoResponse;
import br.com.fiap.shared.PedidoHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class PedidoStep {

    private Response response;
    private PedidoCriadoResponse pedidoResponse;
    private final String ENDPOINT = "http://localhost:8080/pedidos";

    @When("registrar um novo pedido")
    public PedidoCriadoResponse registrarPedido() {
        PedidoRequest pedidoRequest = PedidoHelper.gerarRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
                .when()
                .post(ENDPOINT);
        return response.then().extract().as(PedidoCriadoResponse.class);
    }

    @Then("o pedido é registrado com sucesso")
    public void pedidoRegistradoComSucesso() {
        response.then()
            .statusCode(HttpStatus.CREATED.value())
            .body(matchesJsonSchemaInClasspath("./schemas/PedidoCriadoSchema.json"));
    }

    @Given("que um pedido já foi criado")
    public void pedidoJaCriado(){
        pedidoResponse = registrarPedido();
    }

    @When("requisitar a buscar do pedido")
    public void buscarPedido(){
        response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(ENDPOINT + "/{numeroPedido}", pedidoResponse.numeroPedido());
    }

    @Then("o pedido é exibido com sucesso")
    public void pedidoExibidoComSucesso(){
        response.then()
            .statusCode(HttpStatus.OK.value())
            .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));

    }

    @When("requisitar o acompanhamentos dos pedidos")
    public void requisitarPedidosNaoFinalizados(){
        response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(ENDPOINT + "/follow-up");
    }

    @Then("os pedidos devem ser apresentados")
    public void pedidosFollowUp(){
        response.then()
            .statusCode(HttpStatus.OK.value())
            .body(matchesJsonSchemaInClasspath("./schemas/PedidosFollowUpSchema.json"));
    }

    @Then("apos 10 segundos o pagamento é processado")
    public void processarPedido() throws InterruptedException{
        SECONDS.sleep(12);

        Response consultaResponse = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(ENDPOINT + "/statePayment/{orderNumber}", pedidoResponse.numeroPedido());

        consultaResponse.then().statusCode(HttpStatus.OK.value());

        String status = consultaResponse.jsonPath().getString("statusConfirmacaoPagamento");
        assertEquals("APROVADO", status);
    }

}
