package com.example.spring.usuarios.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.usuarios.adapter.UsuarioAdapter;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;
import com.example.spring.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {
	private static final Logger logger = Logger.getLogger("UsuarioController.class");
	
	@Autowired
	UsuarioService servicio;
	
	@Autowired
	UsuarioAdapter adaptador;
	
	@GetMapping
	public List<UsuarioDTO> findAll() {
		logger.info("----- LISTADO DE USUARIOS (GET) -----");
		return adaptador.listaADTO(servicio.findAll());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {
		
		//Optional<Usuario> usuarioOptional = servicio.deleteById(id);
		
//		if (usuarioOptional.isPresent()) {
//			return ResponseEntity.ok(usuarioOptional.get());
//		} else {
//			throw new UsuarioNotFoundException();
//		}
		
		return null;

	}
}
