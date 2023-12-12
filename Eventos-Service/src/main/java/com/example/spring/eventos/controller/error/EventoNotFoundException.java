package com.example.spring.eventos.controller.error;

public class EventoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EventoNotFoundException() {
		super("No existe el evento en la base de datos");
	}

	public EventoNotFoundException(Long id) {
		super("No existe el evento con id" + id + "  en la base de datos");
	}

	public EventoNotFoundException(String mensaje) {
		super(mensaje);
	}
}
