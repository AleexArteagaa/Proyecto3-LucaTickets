package com.example.spring.eventos.controller.error;

public class EventoRepetidoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EventoRepetidoException() {
		super("Ya existe ese evento en la base de datos");
	}
	public EventoRepetidoException(String mensaje ) {
		super(mensaje);
	}	
}
