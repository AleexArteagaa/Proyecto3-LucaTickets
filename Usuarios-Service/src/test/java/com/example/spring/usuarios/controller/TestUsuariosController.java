package com.example.spring.usuarios.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.usuarios.controller.error.InvalidPasswordException;
import com.example.spring.usuarios.controller.error.UsuarioRepetidoException;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.service.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUsuariosController {

	@MockBean
	private UsuarioService servicio;

	@Autowired
	private MockMvc mockMvc;

	@Test
    public void testAltaUsuarioExitoso() throws Exception {
        when(servicio.save(any())).thenReturn(new Usuario());

        String usuarioJson = "{\r\n"
        		+ "    \"nombre\": \"nuevoUsuario\",\r\n"
        		+ "    \"apellido\": \"nuevoUsuario\",\r\n"
        		+ "    \"mail\": \"nuevoUsuario@gmail.com\",\r\n"
        		+ "    \"contrasenia\": \"1234\"\r\n"
        		+ "}";
        
        mockMvc.perform(post("/usuario")
                .content(usuarioJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

	@Test
	void testAltaUsuarioRepetido() throws Exception {
		String errorMessage = "Epic Fail: Ya existe un usuario con ese correo electronico";

		when(servicio.save(any())).thenThrow(new UsuarioRepetidoException());

		String usuarioJson = "{\r\n" + "    \"nombre\": \"pruebaUsuario\",\r\n" + "    \"apellido\": \"Usuario\",\r\n"
				+ "    \"mail\": \"usuario@gmail.com\",\r\n" + "    \"contrasenia\": \"1234\",\r\n"
				+ "    \"fechaAlta\": \"2023-12-02\"\r\n" + "}";

		mockMvc.perform(post("/usuario").content(usuarioJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message").value(errorMessage));
	}

	@Test
	void testEditarInvalidPassword() throws Exception {
		String errorMessage = "Epic Fail: La contraseña debe tener al menos 4 caracteres";

		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setIdUsuario(9L);

		when(servicio.findById(9L)).thenReturn(usuarioExistente);
		when(servicio.save(any())).thenThrow(new InvalidPasswordException());

		String usuarioJson = "{\r\n" + "    \"nombre\": \"pruebaUsuario\",\r\n" + "    \"apellido\": \"Usuario\",\r\n"
				+ "    \"mail\": \"usuarioExcepcion@gmail.com\",\r\n" + "    \"contrasenia\": \"123\",\r\n"
				+ "    \"fechaAlta\": \"2023-12-02\"\r\n" + "}";

		mockMvc.perform(put("/usuario/{id}", 9).content(usuarioJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value(errorMessage));
	}

	@Test
	void testEditarUsuarioExitoso() throws Exception {
		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setIdUsuario(9L);

		when(servicio.findById(9L)).thenReturn(usuarioExistente);
		when(servicio.save(any())).thenReturn(usuarioExistente);

		String usuarioJson = "{\r\n" + "    \"nombre\": \"pruebaUsuario\",\r\n" + "    \"apellido\": \"Usuario\",\r\n"
				+ "    \"mail\": \"usuarioNuevooo@gmail.com\",\r\n" + "    \"contrasenia\": \"1234\",\r\n"
				+ "    \"fechaAlta\": \"2023-12-02\"\r\n" + "}";

		mockMvc.perform(put("/usuario/{id}", 9).content(usuarioJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testEditarUsuarioRepetido() throws Exception {
		String errorMessage = "Epic Fail: Ya existe un usuario con ese correo electronico";
		
		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setIdUsuario(8L);

		when(servicio.findById(8L)).thenReturn(usuarioExistente);
		when(servicio.save(any())).thenThrow(new UsuarioRepetidoException());

		String usuarioJson = "{\r\n"
				+ "    \"nombre\": \"pruebaUsuario\",\r\n"
				+ "    \"apellido\": \"Usuario\",\r\n"
				+ "    \"mail\": \"usuario@gmail.com\",\r\n"
				+ "    \"contrasenia\": \"1234\",\r\n"
				+ "    \"fechaAlta\": \"2023-12-02\"\r\n"
				+ "}";

		mockMvc.perform(put("/usuario/{id}", 8)
				.content(usuarioJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isInternalServerError())
				.andExpect(jsonPath("$.message")
						.value(errorMessage));
	}

}
