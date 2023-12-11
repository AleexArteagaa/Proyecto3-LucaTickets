package com.example.spring.pago.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;
import com.example.spring.pago.service.PagoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
@Tag(name = "Pago", description = "Operaciones relacionadas con la venta de eventos")
public class PagoController {
	private static final Logger logger = LoggerFactory.getLogger(PagoController.class);

	@Autowired
	PagoService serv;

	// /pago?idUsuario=2&idEvento=3

	@Operation(summary = "Realizar un pago")
	@ApiResponse(responseCode = "200", description = "Pago exitoso")
	@ApiResponse(responseCode = "500", description = "Servidor no disponible")
	@CircuitBreaker(name = "evento", fallbackMethod = "circuitBreaker")
	@PostMapping
	public TarjetaResponse realizarPago(@RequestParam Long idUsuario, @RequestParam Long idEvento,
			@Valid @RequestBody Tarjeta tarjeta) {

		TarjetaResponse tarjetaResponse = serv.realizarPago(tarjeta, idUsuario, idEvento);

		logger.info(tarjetaResponse.toString());

		return tarjetaResponse;
	}
    

	@Operation(summary = "Fallback en caso de error en realizarPago")
	@ApiResponse(responseCode = "200", description = "Respuesta de fallback exitosa")
	@ApiResponse(responseCode = "500", description = "Servidor no disponible")
	private TarjetaResponse circuitBreaker(RuntimeException e) {
		System.out.println("----------- circuitBreakerEvento");
		TarjetaResponse response = new TarjetaResponse();
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String timestampFormateado = ahora.format(formatter);

		response.setError("Servidor no disponible");
		response.setStatus("500");
		response.setTimestamp(timestampFormateado);
		response.setMessage("Lo sentimos, en estos momentos no estan disponibles los servidores. Intentelo más tarde");
    	return response; 

    }

}
