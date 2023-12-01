package com.example.spring.eventos.controller.error;

public class RecintoNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RecintoNotFoundException() {
		super("No existe el recinto en la base de datos");
	}
	public RecintoNotFoundException(String mensaje ) {
		super(mensaje);
	}	
}
