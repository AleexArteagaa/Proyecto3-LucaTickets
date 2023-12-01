package com.example.spring.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {

	@Autowired
	UsuarioService servicio;
	
}
