package com.daniel.gvendas.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String NOT_BLANK = "NotBlank";
	private static final String LENGTH = "Length";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> errors = generateListErrors(ex.getBindingResult());

		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> generateListErrors(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();

		bindingResult.getFieldErrors().forEach(fieldError -> {
			String msgUsuario = handlingErrorUserMessage(fieldError);
			String msgDev = fieldError.toString();
			errors.add(new Error(msgUsuario, msgDev));
		});

		return errors;
	}

	private String handlingErrorUserMessage(FieldError fieldError) {
		if (fieldError.getCode().equals(NOT_BLANK)) {
			return fieldError.getDefaultMessage().concat(" é obrigatório");
		}

		if (fieldError.getCode().equals(LENGTH)) {
			return fieldError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres",
					fieldError.getArguments()[2], fieldError.getArguments()[1]));
		}
		return fieldError.toString();
	}
}
