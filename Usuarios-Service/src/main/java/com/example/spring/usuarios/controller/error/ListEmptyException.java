package com.example.spring.usuarios.controller.error;

public class ListEmptyException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ListEmptyException() {
		super("La lista de usuarios está vacía.");
	}
	
}
