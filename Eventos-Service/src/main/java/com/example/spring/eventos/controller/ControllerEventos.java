package com.example.spring.eventos.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.adapter.EventoAdapter;
import com.example.spring.eventos.response.EventoDTO;
import com.example.spring.eventos.response.EventoListadoDTO;
import com.example.spring.eventos.service.ServiceEventos;
import com.example.spring.eventos.service.ServiceRecinto;
import com.example.spring.eventos.utilidades.Utilidades;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/evento")
@Tag(name = "Eventos", description = "Operaciones relacionadas con eventos")
public class ControllerEventos {

	private static final Logger logger = LoggerFactory.getLogger(ControllerEventos.class);

	@Autowired
	private ServiceRecinto serviceRecinto;

	@Autowired
	private ServiceEventos serviceEventos;

	private EventoAdapter adapter = new EventoAdapter();

	/**
	 * Guarda un nuevo evento mediante una solicitud HTTP POST. El método utiliza la
	 * información proporcionada en el objeto {@code EventoDTO} para crear y
	 * almacenar un nuevo evento en la base de datos. Se realiza la validación del
	 * cuerpo de la solicitud con la anotación {@code @Valid}. La respuesta incluye
	 * el evento creado y la ubicación del recurso en el encabezado de respuesta.
	 *
	 * @param eventoDTO Objeto de transferencia de datos (DTO) que contiene la
	 *                  información del evento a ser creado.
	 * @return ResponseEntity que encapsula la respuesta HTTP, incluyendo el evento
	 *         creado y la ubicación del recurso.
	 */

	@PostMapping
	@Operation(summary = "Crear un nuevo evento", description = "Crea un nuevo evento con la información proporcionada.")
	@ApiResponse(responseCode = "201", description = "Evento creado exitosamente")
	@ApiResponse(responseCode = "400", description = "Solicitud inválida")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	public ResponseEntity<?> save(@Valid @RequestBody EventoDTO eventoDTO) {

		logger.info("------ Alta de evento (POST)");
		Recinto recinto = serviceRecinto.obtenerPorNombre(eventoDTO.getRecinto());

		eventoDTO.getPrecioMinimo();

		Evento evento = new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcionCorta(),
				eventoDTO.getDescripcionExtendida(), eventoDTO.getFoto(), eventoDTO.getFechaEvento(),
				eventoDTO.getHoraEvento(), eventoDTO.getPrecioMinimo(), eventoDTO.getPrecioMaximo(),
				eventoDTO.getNormas(), recinto);

		Evento result = this.serviceEventos.save(evento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getIdEvento()).toUri();

		return ResponseEntity.created(location).body(adapter.of(result));
	}

	/**
	 * Edita un evento existente en base a su ID.
	 *
	 * @param id        ID del evento a editar
	 * @param eventoDTO Datos actualizados del evento
	 * @return ResponseEntity con el evento editado
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Editar un evento", description = "Edita un evento existente en base a su ID.")
	@ApiResponse(responseCode = "200", description = "Evento editado exitosamente")
	@ApiResponse(responseCode = "400", description = "Solicitud inválida")
	@ApiResponse(responseCode = "404", description = "Evento no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	public ResponseEntity<EventoListadoDTO> editarEvento(
			@Parameter(description = "ID del evento a editar") @PathVariable Long id,
			@Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody EventoDTO eventoDTO) {

		logger.info("------ Editar evento (PUT)");
		Evento evento = serviceEventos.findById(id);

		Recinto recinto = serviceRecinto.obtenerPorNombre(eventoDTO.getRecinto());

		evento.setNombre(eventoDTO.getNombre());
		evento.setDescripcionCorta(eventoDTO.getDescripcionCorta());
		evento.setDescripcionExtendida(eventoDTO.getDescripcionExtendida());
		evento.setFoto(eventoDTO.getFoto());
		evento.setFechaEvento(eventoDTO.getFechaEvento());
		evento.setHoraEvento(eventoDTO.getHoraEvento());
		evento.setPrecioMinimo(eventoDTO.getPrecioMinimo());
		evento.setPrecioMaximo(eventoDTO.getPrecioMaximo());
		evento.setNormas(eventoDTO.getNormas());
		evento.setRecinto(recinto);

		Evento result = this.serviceEventos.save(evento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getIdEvento()).toUri();

		return ResponseEntity.created(location).body(adapter.of(result));
	}

	/**
	 * Recupera una lista de todos los eventos mediante una solicitud HTTP GET. El
	 * método obtiene todos los eventos almacenados en la base de datos y los
	 * transforma en objetos {@code EventoListadoDTO} antes de devolver la lista
	 * resultante. La información detallada de cada evento se simplifica para su
	 * presentación en listas o vistas generales.
	 *
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la
	 *         información simplificada de todos los eventos.
	 */
	@GetMapping
	@Operation(summary = "Obtener todos los eventos", description = "Obtiene la lista de todos los eventos disponibles.")
	@ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	public List<EventoListadoDTO> findAll() {
		logger.info("------ Listado de eventos (GET) ");
		return adapter.listaADTO(serviceEventos.findAll());
	}

	/**
	 * Recupera una lista de eventos filtrados por nombre mediante una solicitud
	 * HTTP GET. El método busca eventos cuyo nombre coincida total o parcialmente
	 * con el parámetro proporcionado. La información detallada de cada evento se
	 * transforma en objetos {@code EventoListadoDTO} antes de devolver la lista
	 * resultante.
	 *
	 * @param nombre Nombre o parte del nombre de los eventos a ser buscados.
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la
	 *         información simplificada de los eventos coincidentes con el nombre
	 *         proporcionado.
	 */
	@GetMapping("/nombre/{nombre}")
	@Operation(summary = "Buscar eventos por nombre", description = "Obtiene la lista de eventos que coinciden con el nombre proporcionado.")
	@ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
	@ApiResponse(responseCode = "404", description = "No se encontraron eventos con el nombre especificado")
	public List<EventoListadoDTO> findByNombre(@Parameter(name = "nombre", description = "Nombre del evento a buscar", required = true)@PathVariable String nombre) {
		logger.info("------ Listado de eventos por nombre (GET) ");
		List<Evento> eventoNombre = serviceEventos.findByNombre(nombre);

		return adapter.of(eventoNombre);
	}

	/**
	 * Recupera una lista de eventos filtrados por id mediante una solicitud
	 * HTTP GET. El método busca eventos cuyo id coincida con el parámetro
	 * proporcionado. La información detallada de cada evento se transforma en
	 * objetos {@code EventoListadoDTO} antes de devolver la lista resultante.
	 *
	 * @param id Id de los eventos a ser buscados.
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la
	 *         información simplificada de los eventos coincidentes con el id
	 *         proporcionado.
	 */         
	@GetMapping("/{id}")
	@Operation(summary = "Buscar eventos por id", description = "Obtiene la lista de eventos que coinciden con el id proporcionado.")
	@ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
	@ApiResponse(responseCode = "404", description = "No se encontraron eventos con el id especificado")
	public EventoListadoDTO findById(@Parameter(name = "id", description = "Id del evento a buscar")@PathVariable Long id) {
		logger.info("------ Listado de eventos por id (GET) ");
		Evento evento = serviceEventos.findById(id);

		return adapter.of(evento);
	}

	@GetMapping("/recinto/{nombre}")
	public Recinto findRecintoByNombre(@PathVariable String nombre) {
		logger.info("------ Buscar recinto por nombre (GET) ");

		return serviceRecinto.obtenerPorNombre(nombre);
	}
	
	/**
	 * Recupera una lista de eventos filtrados por genero mediante una solicitud
	 * HTTP GET. El método busca eventos cuyo genero coincida total o parcialmente
	 * con el parámetro proporcionado. La información detallada de cada evento se
	 * transforma en objetos {@code EventoListadoDTO} antes de devolver la lista
	 * resultante.
	 *
	 * @param genero Nombre o parte del nombre del genero de los eventos a ser buscados.
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la
	 *         información simplificada de los eventos coincidentes con el genero
	 *         proporcionado.
	 */
	@GetMapping("/genero/{genero}")
	@Operation(summary = "Buscar eventos por genero", description = "Obtiene la lista de eventos que coinciden con el genero proporcionado.")
	@ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
	@ApiResponse(responseCode = "404", description = "No se encontraron eventos con el genero especificado")
	public List<EventoListadoDTO> findByGenero(@Parameter(name = "genero", description = "Genero del evento a buscar", required = true)@PathVariable String genero) {
		logger.info("------ Buscar evento por genero (GET) ");
		List<Evento> eventoGenero = serviceEventos.findByGenero(genero);

		return adapter.of(eventoGenero);
	}

	@Operation(summary = "Eliminar evento por ID", description = "Elimina al evento con el ID indicado")
	@ApiResponse(responseCode = "200", description = "Evento eliminado correctamente")
	@ApiResponse(responseCode = "404", description = "Recurso no encontrado")
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteById(
			@Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
		logger.info("----- BORRADO DE USUARIO (DELETE) -----");
		Evento evento = serviceEventos.findById(id);
		serviceEventos.deleteById(id);
		return Utilidades.eventoEliminadoJson(adapter.of(evento));
	}

	/**
	 * Recupera una lista de eventos filtrados por ciudad mediante una solicitud
	 * HTTP GET. El método busca eventos cuya ciudad coincida total o parcialmente
	 * con el parámetro proporcionado. La información detallada de cada evento se
	 * transforma en objetos {@code EventoListadoDTO} antes de devolver la lista
	 * resultante.
	 *
	 * @param ciudad Nombre o parte del nombre de la ciudad de los eventos a ser buscados.
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la
	 *         información simplificada de los eventos coincidentes con el nombre
	 *         de la ciudad proporcionada.
	 */
	@GetMapping("/ciudad/{ciudad}")
	@Operation(summary = "Buscar eventos por ciudad", description = "Obtiene la lista de eventos que coinciden con la ciudad proporcionada.")
	@ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
	@ApiResponse(responseCode = "404", description = "No se encontraron eventos con la ciudad especificado")
	public List<EventoListadoDTO> findByCiudad(@Parameter(name = "ciudad", description = "Ciudad del evento a buscar", required = true)@PathVariable String ciudad) {
		logger.info("------ Buscar evento por ciudad (GET) ");
		List<Evento> eventoCiudad = serviceEventos.findByCiudad(ciudad);

		return adapter.of(eventoCiudad);
	}
}
