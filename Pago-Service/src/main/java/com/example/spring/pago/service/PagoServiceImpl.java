package com.example.spring.pago.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.spring.pago.feignclients.BancoFeignClient;
import com.example.spring.pago.feignclients.EventoFeignClient;
import com.example.spring.pago.feignclients.UsuarioFeignClient;
import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;

public class PagoServiceImpl implements PagoService {
	@Autowired
	BancoFeignClient bancoFeign;
	
	 @Autowired 
	 EventoFeignClient eventoFeign;
	 
	 @Autowired
	 UsuarioFeignClient usuarioFeign;
	
	public TarjetaResponse realizarPago(Tarjeta tarjeta) {		
		
		
		return bancoFeign.obtenerDatosValidacion(tarjeta);
	}
}
