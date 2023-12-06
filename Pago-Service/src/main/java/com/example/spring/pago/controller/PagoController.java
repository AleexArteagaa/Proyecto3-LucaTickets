package com.example.spring.pago.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;
import com.example.spring.pago.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
public class PagoController {
	private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
	
	@Autowired
	PagoService serv;
	
	// /pago?idUsuario=2&idEvento=3
	@PostMapping
	public ResponseEntity<TarjetaResponse> realizarPago(@RequestParam Long idUsuario, @RequestParam Long idEvento, @Valid @RequestBody Tarjeta tarjeta){
		
		TarjetaResponse tarjetaResponse = serv.realizarPago(tarjeta, idUsuario, idEvento);
		
		if (tarjeta == null) {
        	return ResponseEntity.badRequest().build();
        }
        else {
        	return ResponseEntity.ok(tarjetaResponse);
        }
	}
}
