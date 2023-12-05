package com.example.spring.eventos.controller.error;

public class EventosIsEmptyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EventosIsEmptyException() {
		super("No existe el evento");
	}
	public EventosIsEmptyException(Long id) {
		super("No existe el evento: " +id);
	}
}
