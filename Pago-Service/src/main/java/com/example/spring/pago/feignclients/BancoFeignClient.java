package com.example.spring.pago.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.model.Token;
import com.example.spring.pago.response.TarjetaResponse;

@FeignClient(name = "banco-service", url = "http://banco-env.eba-3zvamy8n.eu-west-3.elasticbeanstalk.com")
public interface BancoFeignClient {

	@PostMapping("/pasarela/validacion")
	public TarjetaResponse obtenerDatosValidacion(@RequestHeader("Authorization") String token,
			@RequestBody Tarjeta tarjeta);

	@PostMapping("/pasarela/validaruser?user=Grupo02&password=AntoniosRules")
	public Token getToken();
}
