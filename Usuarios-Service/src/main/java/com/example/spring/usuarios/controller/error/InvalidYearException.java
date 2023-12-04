package com.example.spring.usuarios.controller.error;

public class InvalidYearException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidYearException() {
		super("Epic Fail: El año debe ser igual o superior al 2000");
	}

	public InvalidYearException(int year) {
		super("Epic Fail: El año " + year + " debe tener ser igual o superior al 2000");
	}
}
