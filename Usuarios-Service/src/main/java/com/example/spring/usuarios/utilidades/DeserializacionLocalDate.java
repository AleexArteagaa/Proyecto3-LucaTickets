package com.example.spring.usuarios.utilidades;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DeserializacionLocalDate extends JsonDeserializer<LocalDate>{

	/**
	 * Este método nos permite deserializar la fecha del eventoDTO para insertarla en la base de datos.
	 */
	
	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String fechaTexto = p.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fechaTexto, formatter);
	}

	
	
}
