package com.example.spring.pago.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.pago.feignclients.BancoFeignClient;
import com.example.spring.pago.feignclients.EventoFeignClient;
import com.example.spring.pago.feignclients.UsuarioFeignClient;
import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;
import com.example.spring.pago.service.PagoService;

import feign.FeignException;
import feign.Request;
import feign.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPagoController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PagoService servicio;

	@MockBean
	private UsuarioFeignClient usuarioFeign;

	@MockBean
	private EventoFeignClient eventoFeign;

	@MockBean
	private BancoFeignClient bancoFeign;

	@Test
	public void testNumeroTarjetaNoValido() throws Exception {
	    String errorMessage = "El número de la tarjeta no es válido";
	    
	    String tarjetaJson = "{\r\n"
	            + "  \"nombreTitular\": \"Alejandro\",\r\n"
	            + "  \"numeroTarjeta\": \"424-5678-9012-3456\",\r\n"
	            + "  \"mesCaducidad\": \"12\",\r\n"
	            + "  \"yearCaducidad\": \"2025\",\r\n"
	            + "  \"cvv\": \"124\",\r\n"
	            + "  \"emisor\": \"VISA\",\r\n"
	            + "  \"concepto\": \"Compra en línea\",\r\n"
	            + "  \"cantidad\": 100.00\r\n"
	            + "}";
	    
	    mockMvc.perform(
	            post("/pago?idUsuario=3&idEvento=1").content(tarjetaJson).contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.message").value(errorMessage))
	            .andExpect(jsonPath("$.status").value("400"));
	}
	
	@Test
	public void testCvvNoValido() throws Exception {
	    String errorMessage = "El formato del CVV no es válido";
	    
	    String tarjetaJson = "{\r\n"
	            + "  \"nombreTitular\": \"Alejandro\",\r\n"
	            + "  \"numeroTarjeta\": \"4245-5678-9012-3456\",\r\n"
	            + "  \"mesCaducidad\": \"12\",\r\n"
	            + "  \"yearCaducidad\": \"2025\",\r\n"
	            + "  \"cvv\": \"12443\",\r\n"
	            + "  \"emisor\": \"VISA\",\r\n"
	            + "  \"concepto\": \"Compra en línea\",\r\n"
	            + "  \"cantidad\": 100.00\r\n"
	            + "}";
	}
	
    @Test
    public void testMesCaducidadNoCorrecto() throws Exception {
        String errorMessage = "El mes (caducidad) no es correcto";

        String tarjetaJson = "{\r\n"
                + "  \"nombreTitular\": \"Alejandro\",\r\n"
                + "  \"numeroTarjeta\": \"4242567890123456\",\r\n"
                + "  \"mesCaducidad\": \"15\",\r\n"
                + "  \"yearCaducidad\": \"2025\",\r\n"
                + "  \"cvv\": \"124\",\r\n"
                + "  \"emisor\": \"VISA\",\r\n"
                + "  \"concepto\": \"Compra en línea\",\r\n"
                + "  \"cantidad\": 100.00\r\n"
                + "}";

	    mockMvc.perform(
	            post("/pago?idUsuario=3&idEvento=1").content(tarjetaJson).contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.message").value(errorMessage))
	            .andExpect(jsonPath("$.status").value("400"));
	}
	
	@Test
	public void testMesNoValido() throws Exception {
	    String errorMessage = "El mes de caducidad de la tarjeta no es correcto";
	    
	    String tarjetaJson = "{\r\n"
	            + "  \"nombreTitular\": \"Alejandro\",\r\n"
	            + "  \"numeroTarjeta\": \"4243-5678-9012-3456\",\r\n"
	            + "  \"mesCaducidad\": \"13\",\r\n"
	            + "  \"yearCaducidad\": \"2025\",\r\n"
	            + "  \"cvv\": \"124\",\r\n"
	            + "  \"emisor\": \"VISA\",\r\n"
	            + "  \"concepto\": \"Compra en línea\",\r\n"
	            + "  \"cantidad\": 100.00\r\n"
	            + "}"; 
    }

    @Test
    public void testYearCaducidadNoCorrecto() throws Exception {
        String errorMessage = "El año (caducidad) no es correcto";

        String tarjetaJson = "{\r\n"
                + "  \"nombreTitular\": \"Alejandro\",\r\n"
                + "  \"numeroTarjeta\": \"4242567890123456\",\r\n"
                + "  \"mesCaducidad\": \"12\",\r\n"
                + "  \"yearCaducidad\": \"2020\",\r\n"
                + "  \"cvv\": \"124\",\r\n"
                + "  \"emisor\": \"VISA\",\r\n"
                + "  \"concepto\": \"Compra en línea\",\r\n"
                + "  \"cantidad\": 100.00\r\n"
                + "}";

	    mockMvc.perform(
	            post("/pago?idUsuario=3&idEvento=1").content(tarjetaJson).contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.message").value(errorMessage))
	            .andExpect(jsonPath("$.status").value("400"));
	}

    @Test
    public void testFechaCaducidadNoPosteriorAlDiaActual() throws Exception {
        String errorMessage = "La fecha de caducidad debe ser posterior al día actual";

        String tarjetaJson = "{\r\n"
                + "  \"nombreTitular\": \"Alejandro\",\r\n"
                + "  \"numeroTarjeta\": \"4242567890123456\",\r\n"
                + "  \"mesCaducidad\": \"12\",\r\n"
                + "  \"yearCaducidad\": \"2022\",\r\n"
                + "  \"cvv\": \"124\",\r\n"
                + "  \"emisor\": \"VISA\",\r\n"
                + "  \"concepto\": \"Compra en línea\",\r\n"
                + "  \"cantidad\": 100.00\r\n"
                + "}";

	    mockMvc.perform(
	            post("/pago?idUsuario=3&idEvento=1").content(tarjetaJson).contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.message").value(errorMessage))
	            .andExpect(jsonPath("$.status").value("400"));
    }

}
