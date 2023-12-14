package com.example.spring.eventos.utilidades;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.spring.eventos.response.EventoListadoDTO;

public class Utilidades {

	public static ResponseEntity<Map<String, Object>> eventoEliminadoJson(EventoListadoDTO evento) {
		
		Map<String, Object> respuesta= new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
			
		respuesta.put("status", status.value());
		respuesta.put("mensaje", "El evento con ID: " + evento.getId() + " ha sido eliminado correctamente"); 
		respuesta.put("info", evento);	
		return new ResponseEntity<>(respuesta,status);
	}

	
	
}
