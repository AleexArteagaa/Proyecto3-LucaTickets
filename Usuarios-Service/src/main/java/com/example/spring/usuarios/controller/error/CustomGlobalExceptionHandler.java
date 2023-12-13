package com.example.spring.usuarios.controller.error;

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

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	@ExceptionHandler(UsuarioNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleUsuarioNotFoundException(UsuarioNotFoundException ex, WebRequest request) {
		logger.error("------ UsuarioNotFoundException()");

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

	@ExceptionHandler(UsuarioRepetidoException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleUsuarioRepetidoException(UsuarioRepetidoException ex, WebRequest request) {
		logger.error("------ UsuarioRepetidoException() ");

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

	@ExceptionHandler(InvalidPasswordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
		logger.error("------ InvalidPasswordException() ");

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

	@ExceptionHandler(InvalidYearException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidYearException(InvalidYearException ex, WebRequest request) {
		logger.error("------ InvalidYearException() ");

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
		customError.setPath(request.getDescription(false));
		logger.error("------------ Mensaje de la excepción: " + ex.getMessage());

		List<String> mensajes = new ArrayList<>();
		if (ex.getMessage().contains("Required request body is missing")) {
			mensajes.add("El cuerpo de la solicitud no puede estar vacío");
		}

		if (ex.getCause() != null) {
			if (ex.getCause().toString().contains("Unexpected character ('")) {
				mensajes.add("Estructura del body errónea");
			}

			if (ex.getCause().toString().contains("fechaAlta")) {
				if (ex.getCause().toString().contains("MonthOfYear")) {
					mensajes.add("El mes debe ser: 1-12");
				}

				if (ex.getCause().toString().contains("DayOfMonth")) {
					mensajes.add("El día debe ser: 1-28/31");
				}

				if (ex.getCause().toString().contains("could not be parsed at index 8")
						|| ex.getCause().toString().contains("could not be parsed at index 5")
						|| ex.getCause().toString().contains("Text '0")) {
					mensajes.add("El año, mes o día no pueden tener valor 0");
				}

				if (!ex.getCause().toString().contains("DayOfMonth")
						&& !ex.getCause().toString().contains("MonthOfYear")) {
					mensajes.add("El formato de la fecha debe ser dd-MM-yyyy");
				}

			}

			if (ex.getCause().toString().contains("Unrecognized token")) {
				mensajes.add("Valor introducido no válido");
			}
		}

		customError.setMessage(mensajes);
		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		logger.info("------ handleMethodArgumentNotValid()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(status.value());
		customError.setError(status.toString());

		// Get all errors indicando el campo en el que falla
		List<String> messages = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			messages.add(error.getField() + ": " + error.getDefaultMessage());
		}
		customError.setMessage(messages);

		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);

		return new ResponseEntity<>(customError, headers, status);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		logger.error("------ MethodArgumentTypeMismatchException()");

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

	@ExceptionHandler(ListEmptyException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleUsuariosIsEmptyException(ListEmptyException ex, WebRequest request) {
		logger.error("------ ListEmptyException() ");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.NOT_FOUND.value());
		customError.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		customError.setMessage(List.of("No se han encontrado usuarios."));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));

		return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
	}

	/*
	 * @ExceptionHandler(NoResourceFoundException.class)
	 * 
	 * @ResponseStatus(HttpStatus.NOT_FOUND) public ResponseEntity<Object>
	 * handleNoResourceFoundException(NoResourceFoundException ex, WebRequest
	 * request) { logger.error("------ ListEmptyException() ");
	 * 
	 * CustomErrorJson customError = new CustomErrorJson();
	 * customError.setTimestamp(new Date());
	 * customError.setStatus(HttpStatus.NOT_FOUND.value());
	 * customError.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
	 * customError.setMessage(List.of("Endpoint no encontrado."));
	 * customError.setPath(request.getDescription(false)); String uri =
	 * request.getDescription(false); uri = uri.substring(uri.lastIndexOf("=") + 1);
	 * customError.setPath(uri);
	 * customError.setJdk(System.getProperty("java.version"));
	 * 
	 * return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND); }
	 */

}