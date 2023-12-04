package com.example.spring.eventos.controller.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	@ExceptionHandler(RecintoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleRecintoNotFoundException(RecintoNotFoundException ex, WebRequest request) {
		logger.error("------ RecintoNotFoundException() ");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.NOT_FOUND.value());
		customError.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		customError.setMessage(List.of(ex.getMessage()));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));
		
		return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EventoRepetidoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleEventoRepetidoException(EventoRepetidoException ex, WebRequest request) {
		logger.error("------ EventoRepetidoException() ");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		customError.setMessage(List.of(ex.getMessage()));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));
		
		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecintoIsNullException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleRecintoIsNullException(RecintoIsNullException ex, WebRequest request) {
		logger.error("------ RecintoIsNullException() ");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		customError.setMessage(List.of(ex.getMessage()));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));
		
		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
		logger.error("------ SQLIntegrityConstraintViolationException() ");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		customError.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		customError.setMessage(List.of(ex.getMessage()));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));
		
		return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleAnioNotValidException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		logger.error("------ AnioNotValidException()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		customError.setMessage(List.of("Valor introducido por parámetro no válido"));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));


		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.error("------ handleHttpRequestMethodNotSupported()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		customError.setError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
		customError.setMessage(List.of("El método HTTP indicado no está soportado"));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));


		return new ResponseEntity<>(customError, headers, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.error("------ handleHttpMessageNotReadable()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		customError.setMessage(List.of("Valor introducido no válido"));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);

		return new ResponseEntity<>(customError, headers, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		logger.info("------ handleMethodArgumentNotValid()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(status.value());
		customError.setError(status.toString());

		List<String> messages = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			messages.add(error.getField() + ": " + error.getDefaultMessage());
		}
		customError.setMessage(messages);
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));

		return new ResponseEntity<>(customError, headers, status);
	}
	

}