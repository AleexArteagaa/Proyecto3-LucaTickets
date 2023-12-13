package com.example.spring.eventos.controller;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.eventos.controller.error.EventoNotFoundException;
import com.example.spring.eventos.controller.error.EventoRepetidoException;
import com.example.spring.eventos.controller.error.EventosIsEmptyException;
import com.example.spring.eventos.controller.error.RecintoNotFoundException;
import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.service.ServiceEventos;
import com.example.spring.eventos.service.ServiceRecinto;

import io.restassured.RestAssured;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerEventosTest {

	@MockBean
	private ServiceRecinto serviceRecinto;

	@MockBean
	private ServiceEventos serviceEventos;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSaveEventoRecintoNulo() throws Exception {

		mockMvc.perform(post("/evento").contentType(MediaType.APPLICATION_JSON).content(
				"{\"nombre\": \"Concierto Melendi\", \"descripcionCorta\": \"Descripción corta del evento\", \"descripcionExtendida\": \"Descripción extendida del evento\", \"foto\": \"URL de la foto del evento\", \"fechaEvento\": \"2023-12-01\", \"horaEvento\": \"18:30\", \"precioMinimo\": 30.5, \"precioMaximo\": 100.0, \"normas\": \"Normas del evento\", \"recinto\": null}"))
				.andExpect(status().isBadRequest());
	}

	@Test
    void testSaveEventoRepetido() throws Exception {

		when(serviceEventos.save(any())).thenThrow(new EventoRepetidoException());
     
        mockMvc.perform(post("/evento")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Concierto Melendi\", \"descripcionCorta\": \"Descripción corta del evento\", \"descripcionExtendida\": \"Descripción extendida del evento\", \"foto\": \"URL de la foto del evento\", \"fechaEvento\": \"2023-12-01\", \"horaEvento\": \"18:30\", \"precioMinimo\": 30.5, \"precioMaximo\": 100.0, \"normas\": \"Normas del evento\", \"recinto\": \"Wizink Center\"}"))
                .andExpect(status().isBadRequest()); 
    }

	@Test
	void testEditarEventoRecintoNotFound() throws Exception {
		String errorMessage = "No existe el recinto en la base de datos";
		
		Evento eventoExistente = new Evento();
		eventoExistente.setIdEvento(3L);
		
		when(serviceEventos.findById(3L)).thenReturn(eventoExistente);
		when(serviceEventos.save(any())).thenThrow(new RecintoNotFoundException());

		String eventoJson = "{\r\n"
				+ "  \"nombre\": \"Concierto Feid\",\r\n"
				+ "  \"descripcionCorta\": \"Descripción corta del evento\",\r\n"
				+ "  \"descripcionExtendida\": \"Descripción extendida del evento\",\r\n"
				+ "  \"foto\": \"URL de la foto del evento\",\r\n"
				+ "  \"fechaEvento\": \"27-02-2023\",\r\n"
				+ "  \"horaEvento\": \"12:30\",\r\n"
				+ "  \"precioMinimo\": \"30.5\",\r\n"
				+ "  \"precioMaximo\": \"100.0\",\r\n"
				+ "  \"normas\": \"Normas del evento\",\r\n"
				+ "  \"recinto\": \"Recinto Inventado\"\r\n"
				+ "}";

		mockMvc.perform(put("/evento/{id}", 3)
				.contentType(MediaType.APPLICATION_JSON)
				.content(eventoJson))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value(errorMessage));
	}

	@Test
	void testEditarEventoExitoso() throws Exception {
		Evento eventoExistente = new Evento();
		eventoExistente.setIdEvento(3L);
		
		Recinto recinto = new Recinto();
		recinto.setIdRecinto(900L);			
		
		when(serviceRecinto.obtenerPorNombre("Wizink Center")).thenReturn(recinto);
		when(serviceEventos.findById(3L)).thenReturn(eventoExistente);
		when(serviceEventos.save(any())).thenReturn(eventoExistente);

		String eventoDTOJson = "{\r\n"
				+ "  \"nombre\": \"Concierto Feid\",\r\n"
				+ "  \"descripcionCorta\": \"Descripción corta del evento\",\r\n"
				+ "  \"descripcionExtendida\": \"Descripción extendida del evento\",\r\n"
				+ "  \"foto\": \"URL de la foto del evento\",\r\n"
				+ "  \"fechaEvento\": \"27-02-2023\",\r\n"
				+ "  \"horaEvento\": \"12:30\",\r\n"
				+ "  \"precioMinimo\": \"30.5\",\r\n"
				+ "  \"precioMaximo\": \"100.0\",\r\n"
				+ "  \"normas\": \"Normas del evento\",\r\n"
				+ "  \"recinto\": \"Wizink Center\"\r\n"
				+ "}";

		mockMvc.perform(put("/evento/{id}", 3).content(eventoDTOJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void testEditarEventoRepetido() throws Exception {
		String errorMessage = "Ya existe ese evento en la base de datos";

		Evento eventoExistente = new Evento();
		eventoExistente.setIdEvento(3L);
		
		when(serviceEventos.findById(3L)).thenReturn(eventoExistente);
		when(serviceEventos.save(any())).thenThrow(new EventoRepetidoException());
		
		String eventoJson = "{\r\n"
				+ "  \"nombre\": \"Concierto Feid\",\r\n"
				+ "  \"descripcionCorta\": \"Descripción corta del evento\",\r\n"
				+ "  \"descripcionExtendida\": \"Descripción extendida del evento\",\r\n"
				+ "  \"foto\": \"URL de la foto del evento\",\r\n"
				+ "  \"fechaEvento\": \"27-02-2023\",\r\n"
				+ "  \"horaEvento\": \"12:30\",\r\n"
				+ "  \"precioMinimo\": \"30.5\",\r\n"
				+ "  \"precioMaximo\": \"100.0\",\r\n"
				+ "  \"normas\": \"Normas del evento\",\r\n"
				+ "  \"recinto\": \"Wizink Center\"\r\n"
				+ "}";
		
		mockMvc.perform(put("/evento/{id}", 3)
				.contentType(MediaType.APPLICATION_JSON)
				.content(eventoJson))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value(errorMessage));
	}

	@Test
    void testListadoEventosVacio() throws Exception {
		
        when(serviceEventos.findAll()).thenThrow(new EventosIsEmptyException());

        mockMvc.perform(get("/evento"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

	@Test
	void testListadoEventosDatos() throws Exception {

		Recinto recinto1 = new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200);

		Evento evento1 = new Evento("Evento 1", "Descripción Corta 1", "Descripción Extendida 1", "foto1.jpg",
				LocalDate.of(2023, 12, 1), LocalTime.of(20, 0), 100.0, 200.0, "Normas 1", recinto1);
		Evento evento2 = new Evento("Evento 2", "Descripción Corta 2", "Descripción Extendida 2", "foto2.jpg",
				LocalDate.of(2023, 12, 2), LocalTime.of(21, 0), 150.0, 250.0, "Normas 2", recinto1);

		List<Evento> listaEventos = Arrays.asList(evento1, evento2);

		when(serviceEventos.findAll()).thenReturn(listaEventos);

		mockMvc.perform(get("/evento")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre").value(evento1.getNombre()))
				.andExpect(jsonPath("$[1].nombre").value(evento2.getNombre()));
	}

	@Test
	void testListaEventosMismaLongitud() throws Exception {
		List<Evento> eventosEnLista = Arrays.asList(new Evento("Evento 1", "Descripción Corta 1",
				"Descripción Extendida 1", "foto1.jpg", LocalDate.of(2023, 12, 1), LocalTime.of(20, 0), 100.0, 200.0,
				"Normas 1", new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200)));
		int numeroEventosEnFichero = 1;

		when(serviceEventos.findAll()).thenReturn(eventosEnLista);

		mockMvc.perform(get("/evento")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(numeroEventosEnFichero)));
	}

	@Test
	void testFindByNombreCorrecto() throws Exception {

		String nombreCorrecto = "Evento 2";
		Recinto recinto1 = new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200);
		Evento evento2 = new Evento("Evento 2", "Descripción Corta 2", "Descripción Extendida 2", "foto2.jpg",
				LocalDate.of(2023, 12, 2), LocalTime.of(21, 0), 150.0, 250.0, "Normas 2", recinto1);

		when(serviceEventos.findByNombre(nombreCorrecto)).thenReturn(Arrays.asList(evento2));

		List<Evento> eventosEncontrados = serviceEventos.findByNombre(nombreCorrecto);

		boolean todosLosEventosCoinciden = eventosEncontrados.stream()
				.allMatch(evento -> nombreCorrecto.equals(evento2.getNombre()));

		assertTrue(todosLosEventosCoinciden);
	}

	@Test
	void testFindByEventosNameNotFound() throws Exception {

		String nombreInexistente = "Evento Inexistente";

		when(serviceEventos.findByNombre(nombreInexistente)).thenThrow(new EventoNotFoundException());

		assertThrows(EventoNotFoundException.class, () -> {
			serviceEventos.findByNombre(nombreInexistente);
		});
	}

	@Test
	void testFindByIdCoincide() throws Exception {

		Long id = (long) 800;
		Recinto recinto1 = new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200);
		Evento evento = new Evento("Evento 2", "Descripción Corta 2", "Descripción Extendida 2", "foto2.jpg",
				LocalDate.of(2023, 12, 2), LocalTime.of(21, 0), 150.0, 250.0, "Normas 2", recinto1);
		evento.setIdEvento(id);

		when(serviceEventos.findById(id)).thenReturn(evento);

		mockMvc.perform(get("/evento/{id}", id)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id));

	}

	@Test
	void testFindByIdNulo() throws Exception {

		Long id = (long) 234788234;

		when(serviceEventos.findById(id)).thenThrow(new EventoNotFoundException());

		mockMvc.perform(get("/evento/{id}", id)).andDo(print()).andExpect(status().isNotFound());
	}

	@BeforeEach
	public void setup() {
		RestAssured.port = 8081;
	}

	@Test
	void testFindByNombreFound() throws Exception {

		given().when().get("/evento/nombre/melendi").then().statusCode(200).body("nombre",
				hasItem(containsString("Concierto Melendi")));
	}

	@Test
	void testFindByNombreInexistente() throws Exception {
		given().queryParam("nombre", "nombre_inexistente").when().get("/evento/nombre").then().statusCode(400);
	}

	@Test
	void testFindByNombreError() throws Exception {
		given().queryParam("nombre", "nombre_inexistente").when().get("/nombre/nombre").then().statusCode(404);
	}

	@Test
	void testFindByGeneroCorrecto() throws Exception {

		given().when().get("/evento/genero/corta").then().statusCode(200).body("descripcionCorta",
				hasItem(containsString("corta")));
	}

	@Test
	void testFindByGeneroInexistente() throws Exception {
		given().queryParam("genero", "genero_inexistente").when().get("/evento/genero").then().statusCode(400);
	}

	@Test
	void testFindByGeneroError() throws Exception {
		given().queryParam("genero", "genero_inexistente").when().get("/genero/genero").then().statusCode(404);
	}

	@Test
	void testFindByCiudadCorrecto() throws Exception {

		given().when().get("/evento/ciudad/Madrid").then().statusCode(200).body("recinto.localidad",
				hasItem(containsString("Madrid")));
	}

	@Test
	void testFindByCiudadInexistente() throws Exception {
		given().queryParam("ciudad", "genero_inexistente").when().get("/evento/ciudad").then().statusCode(400);
	}

	@Test
	void testFindByCiudadError() throws Exception {
		given().queryParam("nombre", "nombre_inexistente").when().get("/ciudad/ciudad").then().statusCode(404);
	}
}
