package com.example.spring.eventos.controller.error;

public class RecintoNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RecintoNotFoundException() {
		super("Epic Fail: No existe el recinto");
	}
	public RecintoNotFoundException(Long id) {
		super("Epic Fail: No existe el recinto" +id);
	}	
}
