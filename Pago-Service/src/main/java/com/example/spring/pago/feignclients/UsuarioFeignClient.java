package com.example.spring.pago.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario", url = "http://localhost:4444")
public interface UsuarioFeignClient {
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> getUsuario(@PathVariable Long id);
}
