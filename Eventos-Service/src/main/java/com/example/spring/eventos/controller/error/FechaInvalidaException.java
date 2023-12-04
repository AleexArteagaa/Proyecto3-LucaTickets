package com.example.spring.eventos.controller.error;

import java.time.LocalDate;

public class FechaInvalidaException extends IllegalArgumentException {

    public FechaInvalidaException(LocalDate localDate) {
        super("Fecha no válida: " + localDate);
    }
}