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
	
	@ExceptionHandler(EventoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> EventoNotFoundException(EventoNotFoundException ex, WebRequest request) {
		logger.error("------ EventoNotFoundException() ");

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

	@ExceptionHandler(InvalidYearException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidYearException(InvalidYearException ex,
			WebRequest request) {
		logger.error("------ InvalidYearException()");

		CustomErrorJson customError = new CustomErrorJson();
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		customError.setMessage(List.of("Año no váido. Debe ser mayor del 2000"));
		customError.setPath(request.getDescription(false));
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);
		customError.setJdk(System.getProperty("java.version"));


		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
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

		List<String> mensajesError = new ArrayList<>();

		CustomErrorJson customError = new CustomErrorJson();;
		customError.setTimestamp(new Date());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		if (ex.getCause() != null && ex.getCause().toString().contains("fechaEvento")) {
			if (ex.getCause().toString().contains("MonthOfYear")) {
				mensajesError.add("El mes debe ser: 1-12");
			}
 
			if (ex.getCause().toString().contains("DayOfMonth")) {
				mensajesError.add("El día debe ser: 1-28/31");
			}
			
			if (ex.getCause().toString().contains("could not be parsed at index 8") || ex.getCause().toString().contains("could not be parsed at index 5") || ex.getCause().toString().contains("Text '0")) {
				mensajesError.add("El año, mes o día no pueden tener valor 0");
			}
 
			if (!ex.getCause().toString().contains("DayOfMonth") && !ex.getCause().toString().contains("MonthOfYear")) {
				mensajesError.add("El formato de la fecha debe ser dd-MM-yyyy");
			}
 
		}else if (ex.getMessage().contains("Required request body is missing")) {
			mensajesError.add("El body no puede estar vacío");
		} else if (ex.getCause().toString().contains("Unexpected character ('")) {
			mensajesError.add("Estructura del body errónea");
		}
		if (ex.getCause().toString().contains("Invalid value for HourOfDay")) {
			mensajesError.add("Valor de la hora no válido");
		}
		if (ex.getCause().toString().contains("Invalid value for MinuteOfHour")) {
			mensajesError.add("Valor de los minutos no válido");
		}
		if (ex.getCause().toString().contains("Unrecognized token")) {
			mensajesError.add("Valor introducido no válido");
		}
		if (ex.getCause().toString().contains("java.lang.Double")) {
			mensajesError.add("El precio debe ser un número");
		}
		customError.setMessage(mensajesError);
		System.out.println(ex.getCause());
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