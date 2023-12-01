package com.example.spring.eventos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.eventos.controller.error.EventoRepetidoException;
import com.example.spring.eventos.service.ServiceEventos;
import com.example.spring.eventos.service.ServiceRecinto;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerEventosTest {
	
	
	@Mock
    private ServiceRecinto serviceRecinto;

    @Mock
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

}
