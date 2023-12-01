package com.example.spring.usuarios.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.usuarios.adapter.UsuarioAdapter;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;
import com.example.spring.usuarios.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {
	private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

	@Autowired
	UsuarioService servicio;
	
	@Autowired
	UsuarioAdapter adaptador;
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> altaUsuario(@Valid @RequestBody Usuario usuario) {
		logger.info("------ Alta de usuario (POST)");
		UsuarioDTO result = adaptador.of(servicio.save(usuario));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable int id, @Valid @RequestBody Usuario usuario) {
		logger.info("------ Editar usuario (PUT)");
		UsuarioDTO result = adaptador.of(servicio.save(usuario));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).body(result);
	}
	
}
