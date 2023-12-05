package com.example.spring.usuarios.utilidades;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.spring.usuarios.response.UsuarioDTO;

public class Utilidades {

	public static ResponseEntity<Map<String, Object>> usuarioEliminadoJson(UsuarioDTO usuario) {
		
		Map<String, Object> respuesta= new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
			
		respuesta.put("status", status.value());
		respuesta.put("mensaje", "El usuario con ID: " + usuario.getId() + " ha sido eliminado correctamente"); 
		respuesta.put("info", usuario);	
		return new ResponseEntity<>(respuesta,status);
	}

	
	
}
