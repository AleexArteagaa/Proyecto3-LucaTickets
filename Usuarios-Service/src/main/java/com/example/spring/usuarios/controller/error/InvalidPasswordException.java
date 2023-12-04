package com.example.spring.usuarios.controller.error;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
		super("Epic Fail: La contraseña debe tener al menos 4 caracteres");
	}

	public InvalidPasswordException(String password) {
		super("Epic Fail: La contraseña " + password + " debe tener al menos 4 caracteres");
	}
}
