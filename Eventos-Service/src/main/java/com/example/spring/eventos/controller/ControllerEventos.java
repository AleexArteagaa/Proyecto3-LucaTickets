package com.example.spring.eventos.controller;


import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * Guarda un nuevo evento mediante una solicitud HTTP POST. El método utiliza la información proporcionada en el objeto
	 * {@code EventoDTO} para crear y almacenar un nuevo evento en la base de datos. Se realiza la validación del cuerpo de la
	 * solicitud con la anotación {@code @Valid}. La respuesta incluye el evento creado y la ubicación del recurso en el
	 * encabezado de respuesta.
	 *
	 * @param eventoDTO Objeto de transferencia de datos (DTO) que contiene la información del evento a ser creado.
	 * @return ResponseEntity que encapsula la respuesta HTTP, incluyendo el evento creado y la ubicación del recurso.
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
		
		Evento evento = new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcionCorta(), eventoDTO.getDescripcionExtendida(), eventoDTO.getFoto(), eventoDTO.getFechaEvento(), eventoDTO.getHoraEvento(), eventoDTO.getPrecioMinimo(), eventoDTO.getPrecioMaximo(), eventoDTO.getNormas(), recinto);
		
		Evento result = this.serviceEventos.save(evento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getIdEvento())
				.toUri();
		
		return ResponseEntity.created(location).body(result);
		

	}
	

	/**
	 * Recupera una lista de todos los eventos mediante una solicitud HTTP GET. El método obtiene todos los eventos
	 * almacenados en la base de datos y los transforma en objetos {@code EventoListadoDTO} antes de devolver la lista
	 * resultante. La información detallada de cada evento se simplifica para su presentación en listas o vistas generales.
	 *
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la información simplificada de todos los eventos.
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
	 * Recupera una lista de eventos filtrados por nombre mediante una solicitud HTTP GET. El método busca eventos cuyo nombre
	 * coincida total o parcialmente con el parámetro proporcionado. La información detallada de cada evento se transforma en
	 * objetos {@code EventoListadoDTO} antes de devolver la lista resultante.
	 *
	 * @param nombre Nombre o parte del nombre de los eventos a ser buscados.
	 * @return Lista de objetos {@code EventoListadoDTO} que representan la información simplificada de los eventos
	 *         coincidentes con el nombre proporcionado.
	 */
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar eventos por nombre", description = "Obtiene la lista de eventos que coinciden con el nombre proporcionado.")
    @ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontraron eventos con el nombre especificado")
    @Parameter(name = "nombre", description = "Nombre del evento a buscar", required = true)	
    public List<EventoListadoDTO> findByNombre(@PathVariable String nombre) {
		logger.info("------ Listado de eventos por nombre (GET) ");
	    List<Evento> eventoNombre = serviceEventos.findByNombre(nombre);
	    
	    	return adapter.of(eventoNombre);   	
	}
    
	@GetMapping("/{id}")
	public EventoListadoDTO findById(@PathVariable Long id) {
		logger.info("------ Listado de eventos por id (GET) ");
		Evento evento = serviceEventos.findById(id);
 
		return adapter.of(evento);
	}
	
	@GetMapping("/recinto/{nombre}")
	public Recinto findRecintoByNombre(@PathVariable String nombre) {
		logger.info("------ Buscar recinto por nombre (GET) ");
		
		return serviceRecinto.obtenerPorNombre(nombre);
	}
	


	
	

}
