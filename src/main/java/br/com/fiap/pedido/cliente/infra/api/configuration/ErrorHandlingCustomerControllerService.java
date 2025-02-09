package br.com.fiap.pedido.cliente.infra.api.configuration;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.fiap.pedido.shared.exception.NaoEncontradoException;
import br.com.fiap.pedido.shared.exception.ValidationErrorResponse;
import br.com.fiap.pedido.shared.exception.Violation;

@ControllerAdvice
public class ErrorHandlingCustomerControllerService {

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse notFoundException(NaoEncontradoException e) {

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.add(new Violation());

        return error;
    }
}
