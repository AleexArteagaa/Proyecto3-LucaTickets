package com.example.spring.usuarios.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.usuarios.controller.error.UsuarioNotFoundException;
import com.example.spring.usuarios.controller.error.UsuarioRepetidoException;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;
import com.example.spring.usuarios.service.UsuarioService;
import com.example.spring.usuarios.utilidades.Utilidades;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUsuariosController {

	@MockBean
	private UsuarioService servicio;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testFindByIdParametroIgual() throws Exception {

		Long id = (long) 8;
		Usuario usuarioEsperado = new Usuario(id, "prueba", "prueba", "prueba@gmail.com", "prueba", LocalDate.now());

		when(servicio.findById(id)).thenReturn(usuarioEsperado);

		mockMvc.perform(get("/usuario/{id}", id)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id));

	}

	@Test
	void testFindByIdUsuarioNoExiste() throws Exception {
		Long id = (long) 1;

		when(servicio.findById(id)).thenThrow(new UsuarioNotFoundException(id));

		mockMvc.perform(get("/usuario/{id}", id)).andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	void testDeleteByIdUsuarioNoExiste() throws Exception {
		Long id = (long) 1;

		when(servicio.findById(id)).thenThrow(new UsuarioNotFoundException(id));

		mockMvc.perform(delete("/usuario/{id}", id)).andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	public void testDeleteByIdUsuarioEliminadoCorrectamente() throws Exception {
		
		Long id = (long) 8;
		Usuario usuario = new Usuario(id, "prueba", "prueba", "prueba@gmail.com", "prueba", LocalDate.now());
		when(servicio.findById(id)).thenReturn(usuario);

	    mockMvc.perform(delete("/usuario/{id}", id)).andDo(print()).andExpect(status().isOk());
	    
	    verify(servicio, times(1)).deleteById(id);
	}

}
