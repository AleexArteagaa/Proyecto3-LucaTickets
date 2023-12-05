package com.example.spring.eventos.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/evento")
public class ControllerEventos {

	private static final Logger logger = LoggerFactory.getLogger(ControllerEventos.class);

	@Autowired
	private ServiceRecinto serviceRecinto;

	@Autowired
	private ServiceEventos serviceEventos;

	private EventoAdapter adapter = new EventoAdapter();

	@PostMapping
	public EventoListadoDTO save(@Valid @RequestBody EventoDTO eventoDTO) {
		
		logger.info("------ Alta de evento (POST)");
		Recinto recinto = serviceRecinto.obtenerPorNombre(eventoDTO.getRecinto());
		
		eventoDTO.getPrecioMinimo();

		Evento evento = new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcionCorta(), eventoDTO.getDescripcionExtendida(), eventoDTO.getFoto(), eventoDTO.getFechaEvento(), eventoDTO.getHoraEvento(), eventoDTO.getPrecioMinimo(), eventoDTO.getPrecioMaximo(), eventoDTO.getNormas(), recinto);
		
		Evento result = this.serviceEventos.save(evento);

		return adapter.of(result);
	}

	@GetMapping()
	public List<EventoListadoDTO> findAll() {
		logger.info("------ Listado de eventos (GET) ");
		return adapter.listaADTO(serviceEventos.findAll());
	}

	@GetMapping("/{nombre}")
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

}
