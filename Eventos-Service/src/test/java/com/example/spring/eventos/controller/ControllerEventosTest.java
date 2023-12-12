package com.example.spring.eventos.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.eventos.controller.error.EventoNotFoundException;
import com.example.spring.eventos.controller.error.EventoRepetidoException;
import com.example.spring.eventos.controller.error.EventosIsEmptyException;
import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.service.ServiceEventos;
import com.example.spring.eventos.service.ServiceRecinto;

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
    	
    	
        mockMvc.perform(post("/evento")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Concierto Melendi\", \"descripcionCorta\": \"Descripción corta del evento\", \"descripcionExtendida\": \"Descripción extendida del evento\", \"foto\": \"URL de la foto del evento\", \"fechaEvento\": \"2023-12-01\", \"horaEvento\": \"18:30\", \"precioMinimo\": 30.5, \"precioMaximo\": 100.0, \"normas\": \"Normas del evento\", \"recinto\": null}"))
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

        mockMvc.perform(get("/evento"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value(evento1.getNombre()))
               .andExpect(jsonPath("$[1].nombre").value(evento2.getNombre()));
    }
	
	@Test
	void testListaEventosMismaLongitud() throws Exception {
		List<Evento> eventosEnLista = Arrays.asList(
				new Evento("Evento 1", "Descripción Corta 1", "Descripción Extendida 1", "foto1.jpg", 
                        LocalDate.of(2023, 12, 1), LocalTime.of(20, 0), 100.0, 200.0, "Normas 1", new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200)));
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

		when(serviceEventos.findById(id)).thenThrow(new EventoNotFoundException(id));

		mockMvc.perform(get("/evento/{id}", id)).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	void testEliminarEventoNoExiste() throws Exception {
		
		Long id=5000L;
		
		when(serviceEventos.findById(id)).thenThrow(new EventoNotFoundException());
		
		mockMvc.perform(delete("/evento/{id}",id)).andDo(print()).andExpect(status().isNotFound());
		
		
	}
	
	@Test
	void testEliminarUsuarioCorrecto() throws Exception {
		Long id=5000L;
		
		Evento evento = new Evento("eventoEliminado","eventoEliminado","eventoEliminado","fotoEventoEliminado", LocalDate.now(), LocalTime.now(),10.0,20.0,"prueba",new Recinto());
		
		when(serviceEventos.findById(id)).thenReturn(evento);
		
		mockMvc.perform(delete("/evento/{id}",id)).andDo(print()).andExpect(status().isAccepted());
		
		verify(serviceEventos, times(1)).deleteById(id);
	}
}
