package com.example.spring.usuarios.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.net.URI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.usuarios.adapter.UsuarioAdapter;
import com.example.spring.usuarios.controller.error.CustomErrorJson;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;
import com.example.spring.usuarios.service.UsuarioService;
import com.example.spring.usuarios.utilidades.Utilidades;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuariosController {
	private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

	@Autowired
	UsuarioService servicio;

	@Autowired
	UsuarioAdapter adaptador;

	/**
	 * Método para obtener una lista de todos los usuarios.
	 *
	 * @return Una lista de objetos UsuarioDTO.
	 */
	@Operation(summary = "Obtener todos los usuarios", description = "Obtiene la lista de todos los usuarios disponibles")
	@ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@GetMapping
	public List<UsuarioDTO> findAll() {
		logger.info("----- LISTADO DE USUARIOS (GET) -----");
		return adaptador.listaADTO(servicio.findAll());
	}

	/**
	 * Método para eliminar un usuario por su ID.
	 *
	 * @param id El ID del usuario a eliminar.
	 * @return ResponseEntity con un mapa de datos y el estado HTTP correspondiente.
	 */
	@Operation(summary = "Eliminar usuario por ID", description = "Elimina al usuario con el ID indicado")
	@ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteById(
			@Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
		logger.info("----- BORRADO DE USUARIO (DELETE) -----");
		Usuario usuario = servicio.findById(id);
		servicio.deleteById(id);
		return Utilidades.usuarioEliminadoJson(adaptador.of(usuario));
	}

	/**
	 * Método para buscar un usuario por su ID.
	 *
	 * @param id El ID del usuario a buscar.
	 * @return Un objeto UsuarioDTO correspondiente al usuario encontrado.
	 */
	@Operation(summary = "Buscar usuario por ID", description = "Realiza una búsqueda del usuario con el ID indicado")
	@ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@GetMapping("/{id}")
	public UsuarioDTO findById(@Parameter(description = "ID del usuario a buscar") @PathVariable Long id) {
		logger.info("----- LISTADO DE USUARIO POR ID (GET)-----");

		UsuarioDTO usuario = adaptador.of(servicio.findById(id));

		return usuario;
	}

	/**
	 * Método para dar de alta un nuevo usuario.
	 *
	 * @param usuario El usuario a dar de alta.
	 * @return ResponseEntity con un objeto UsuarioDTO y la respuesta HTTP
	 *         correspondiente.
	 */
	@Operation(summary = "Alta de usuario", description = "Realiza un alta de un usuario")
	@ApiResponse(responseCode = "201", description = "Usuario dado de alta correctamente")
	@ApiResponse(responseCode = "400", description = "Solicitud mal formada")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@PostMapping
	public ResponseEntity<?> altaUsuario(
			@Parameter(description = "Datos del usuario a dar de alta", required = true) @Valid @RequestBody Usuario usuario,
			HttpServletRequest request) {
		logger.info("------ Alta de usuario (POST)");
		UsuarioDTO result = adaptador.of(servicio.save(usuario));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).body(result);

	}

	/**
	 * Método para editar un usuario existente.
	 *
	 * @param id      El ID del usuario a editar.
	 * @param usuario Los datos actualizados del usuario.
	 * @return ResponseEntity con un objeto UsuarioDTO y la respuesta HTTP
	 *         correspondiente.
	 */
	@Operation(summary = "Editar usuario", description = "Realiza una edición de un usuario")
	@ApiResponse(responseCode = "200", description = "Usuario editado correctamente")
	@ApiResponse(responseCode = "400", description = "Solicitud mal formada")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> editarUsuario(
			@Parameter(description = "ID del usuario a editar") @PathVariable Long id,
			@Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody Usuario usuario) {
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
