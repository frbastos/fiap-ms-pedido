package br.com.fiap.pedido.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.fiap.pedido.produto.infra.exceptions.ProdutoEmUsoException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(NaoEncontradoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse notFoundException(NaoEncontradoException e) {
		
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.add(new Violation(e.getMessage()));
		
		return error;
	}

	@ExceptionHandler(ProdutoEmUsoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse notFoundException(ProdutoEmUsoException e) {
		
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.add(new Violation(e.getMessage()));
		
		return error;
	}

	// @ExceptionHandler(PaymentRefusedException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ResponseBody
	// ValidationErrorResponse paymentRefusedException(PaymentRefusedException e) {
		
	// 	ValidationErrorResponse error = new ValidationErrorResponse();
	// 	error.add(new Violation(e.getMessage()));
		
	// 	return error;
	// }
    
}
