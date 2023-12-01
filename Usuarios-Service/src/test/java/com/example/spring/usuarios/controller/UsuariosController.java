package com.example.spring.usuarios.controller;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.usuarios.service.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuariosController {

	@Mock
	private UsuarioService servicio;

	@Autowired
	private MockMvc mockMvc;

}
