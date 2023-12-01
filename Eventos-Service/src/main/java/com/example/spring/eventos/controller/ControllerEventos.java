package com.example.spring.eventos.controller;


import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.response.EventoDTO;
import com.example.spring.eventos.service.ServiceEventos;
import com.example.spring.eventos.service.ServiceRecinto;

import jakarta.validation.Valid;

import com.example.spring.eventos.service.ServiceEventos;


@RestController
@RequestMapping("/evento")
public class ControllerEventos {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerEventos.class);
	
	@Autowired
	private ServiceRecinto serviceRecinto;
	
	@Autowired
	private ServiceEventos serviceEventos;

	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody EventoDTO eventoDTO) {
		
		Recinto recinto = serviceRecinto.obtenerPorNombre(eventoDTO.getNombre());
		
		Evento evento = new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcionCorta(), eventoDTO.getDescripcionExtendida(), eventoDTO.getFoto(), eventoDTO.getFechaEvento(), eventoDTO.getHoraEvento(), eventoDTO.getPrecioMinimo(), eventoDTO.getPrecioMaximo(), eventoDTO.getNormas(), recinto);
		
		Evento result = this.serviceEventos.save(evento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getIdEvento())
				.toUri();
		
		return null;
		
	}

	private static final Logger log = LoggerFactory.getLogger(ControllerEventos.class);
	
	@Autowired
	private ServiceEventos eventoService;
	

	
}
