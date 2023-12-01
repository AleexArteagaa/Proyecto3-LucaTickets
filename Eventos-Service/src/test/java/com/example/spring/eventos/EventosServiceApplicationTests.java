package com.example.spring.eventos;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.service.ServiceEventos;


@SpringBootTest
@AutoConfigureMockMvc
class EventosServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceEventos serv;

	@Test
    void testListadoEditoresVacio() throws Exception {
        when(serv.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/juego"))
                .andDo(print())
                .andExpect(status().isNoContent()); 
    }
	
	@Test
    void testGetEventosConDatos() throws Exception {

		Recinto recinto1 = new Recinto("Recinto 1", "Madrid", "Calle Principe de Vergara", "Local", 200);
		
        Evento evento1 = new Evento("Evento 1", "Descripción Corta 1", "Descripción Extendida 1", "foto1.jpg", 
                                     LocalDate.of(2023, 12, 1), LocalTime.of(20, 0), 100.0, 200.0, "Normas 1", recinto1);
        Evento evento2 = new Evento("Evento 2", "Descripción Corta 2", "Descripción Extendida 2", "foto2.jpg", 
                                     LocalDate.of(2023, 12, 2), LocalTime.of(21, 0), 150.0, 250.0, "Normas 2", recinto1);
        
        List<Evento> listaEventos = Arrays.asList(evento1, evento2);

        when(serv.findAll()).thenReturn(listaEventos);

        mockMvc.perform(get("/juego"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value(evento1.getNombre()))
               .andExpect(jsonPath("$[1].nombre").value(evento2.getNombre()));
    }
	
	
	
}
