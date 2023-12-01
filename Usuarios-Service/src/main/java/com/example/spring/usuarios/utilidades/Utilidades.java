package com.example.spring.usuarios.utilidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.example.spring.usuarios.response.UsuarioDTO;

public class Utilidades {

	public static Map<String, Object> usuarioEliminadoJson(UsuarioDTO usuario) {
		
		Map<String, Object> respuesta= new HashMap<>();
		respuesta.put("status", 200);
		respuesta.put("mensaje", "El usuario con ID: " + usuario.getId() + " ha sido eliminado correctamente"); 
		respuesta.put("info", usuario);	
		return respuesta;
	}

	
	
}
