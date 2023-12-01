package com.example.spring.usuarios.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.usuarios.adapter.UsuarioAdapter;
import com.example.spring.usuarios.controller.error.UsuarioRepetidoException;
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
	
	@GetMapping
	public List<UsuarioDTO> findAll() {
		logger.info("----- LISTADO DE USUARIOS (GET) -----");
		return adaptador.listaADTO(servicio.findAll());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {

		logger.info("----- BORRADO DE USUARIO (DELETE) -----");
		
		//Optional<Usuario> usuarioOptional = servicio.findById(id);
		
//		if (usuarioOptional.isPresent()) {
//			servicio.deleteById(id);
//			return ResponseEntity.ok(adaptador.of(usuarioOptional.get()));
//		} else {
//			throw new UsuarioNotFoundException();
//		}
		
		servicio.deleteById(id);
		
		return null;

	}
	
	@GetMapping("/{id}")
	public UsuarioDTO findById(@PathVariable Long id) {
		
		logger.info("----- LISTADO DE USUARIO POR ID (GET)-----");
		
		UsuarioDTO usuario= adaptador.of(servicio.findById(id));
		return usuario;
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> altaUsuario(@Valid @RequestBody Usuario usuario) {
		logger.info("------ Alta de usuario (POST)");
		UsuarioDTO result = adaptador.of(servicio.save(usuario));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		logger.info("------ Editar usuario (PUT)");
		Usuario usuarioExistente = servicio.findById(id);
		
		usuarioExistente.setApellido(usuario.getApellido());
		usuarioExistente.setContrasenia(usuario.getContrasenia());
		usuarioExistente.setFechaAlta(usuario.getFechaAlta());
		usuarioExistente.setMail(usuario.getMail());
		usuarioExistente.setNombre(usuario.getNombre());		
		
		UsuarioDTO result = adaptador.of(servicio.save(usuarioExistente));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(result);
	}
	
}
