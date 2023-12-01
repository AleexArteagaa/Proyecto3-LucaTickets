package com.example.spring.eventos.controller.error;

public class RecintoIsNullException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RecintoIsNullException() {
		super("El recinto no puede ser nulo");
	}
	public RecintoIsNullException(String mensaje ) {
		super(mensaje);
	}	
}
