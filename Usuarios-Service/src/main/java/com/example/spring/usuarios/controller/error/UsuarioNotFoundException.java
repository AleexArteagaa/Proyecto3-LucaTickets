package com.example.spring.usuarios.controller.error;

public class UsuarioNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UsuarioNotFoundException() {
		super("Epic Fail: No existe el usuario");
	}
	public UsuarioNotFoundException(Long id) {
		super("Epic Fail: No existe el usuario con id " + id);
	}

}
