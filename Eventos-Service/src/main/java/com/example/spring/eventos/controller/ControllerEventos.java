package com.example.spring.eventos.controller;


import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	EventoAdapter adapter;

	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody EventoDTO eventoDTO) {
		
		Recinto recinto = serviceRecinto.obtenerPorNombre(eventoDTO.getRecinto());
		
		Evento evento = new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcionCorta(), eventoDTO.getDescripcionExtendida(), eventoDTO.getFoto(), eventoDTO.getFechaEvento(), eventoDTO.getHoraEvento(), eventoDTO.getPrecioMinimo(), eventoDTO.getPrecioMaximo(), eventoDTO.getNormas(), recinto);
		
		Evento result = this.serviceEventos.save(evento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getIdEvento())
				.toUri();
		
		return ResponseEntity.created(location).body(result);
		
	}
	
	@GetMapping()
    public List<EventoDTO> eventoList() {
        final List<Evento> all = serviceEventos.findAll();
        return adapter.of(all);
    }
	
	@GetMapping("/{nombre}")
    public List<EventoDTO> nombreList(@PathVariable String nombre) {
		final List<Evento> eventoNombre = serviceEventos.findByNombre(nombre);
        return adapter.of(eventoNombre);
    }
	
	
}
