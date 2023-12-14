package com.example.spring.pago.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.spring.pago.response.UsuarioDTO;

@FeignClient(name = "usuario", url = "http://localhost:4444")
public interface UsuarioFeignClient {
	
	@GetMapping("/usuario/{id}")
	public UsuarioDTO getUsuario(@PathVariable Long id);
}
