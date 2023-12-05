package com.example.spring.eventos.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
		
        when(serviceEventos.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/evento"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
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
}
