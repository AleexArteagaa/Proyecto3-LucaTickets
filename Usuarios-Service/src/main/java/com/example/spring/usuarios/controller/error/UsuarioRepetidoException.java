package com.example.spring.usuarios.controller.error;

public class UsuarioRepetidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioRepetidoException() {
		super("Epic Fail: Ya existe un usuario con ese correo electronico");
	}
}
