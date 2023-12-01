package com.example.spring.eventos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.eventos.service.ServiceEventos;


@RestController
@RequestMapping("/evento")
public class ControllerEventos {

	private static final Logger log = LoggerFactory.getLogger(ControllerEventos.class);
	
	@Autowired
	private ServiceEventos eventoService;
	

	
}
