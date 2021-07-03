package com.daniel.gvendas.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String NOT_BLANK = "NotBlank";
	private static final String LENGTH = "Length";
	private static final String NOT_NULL = "NotNull";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> errors = generateListErrors(ex.getBindingResult());

		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String msgUsuario = "Recurso não encontrado";
		String msgDev = ex.toString();	
		
		List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDev));
		
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String msgUsuario = "Recurso não encontrado";
		String msgDev = ex.toString();	
		
		List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDev));
		
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(BusinessRulesException.class)
	public ResponseEntity<Object> handleBusinessRulesException(BusinessRulesException ex, WebRequest request) {
		String msgUsuario = ex.getMessage();
		String msgDev = ex.getMessage();	
		
		List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDev));
		
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
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
		
		if (fieldError.getCode().equals(NOT_NULL)) {
			return fieldError.getDefaultMessage().concat(" é obrigatório");
		}
		
		return fieldError.toString();
	}
}
