package com.example.spring.pago.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "evento", url = "http://localhost:3333")
public interface EventoFeignClient {
	
	@GetMapping("/evento/{id}")
	public ResponseEntity<?> getEvento(@PathVariable Long id);
}