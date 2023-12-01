package com.example.spring.eventos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.adapter.EventoAdapter;
import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.response.EventoDTO;
import com.example.spring.eventos.service.ServiceEventos;

@RestController
@RequestMapping("/evento")
public class ControllerEventos {

	private static final Logger log = LoggerFactory.getLogger(ControllerEventos.class);
	
	@Autowired
	private ServiceEventos servicio;
	
	@Autowired
	EventoAdapter adapter;
	
	@Autowired
	Evento evento;
	
	@GetMapping()
    public List<EventoDTO> list() {
        final List<Evento> all = servicio.findAll();
        return adapter.of(all);
    }
	
	
}
