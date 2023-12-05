package com.example.spring.pago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.pago.feignclients.BancoFeignClient;
import com.example.spring.pago.feignclients.EventoFeignClient;
import com.example.spring.pago.feignclients.UsuarioFeignClient;
import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.model.UsuarioEvento;
import com.example.spring.pago.repository.UsuarioEventoRepository;
import com.example.spring.pago.response.EventoListadoDTO;
import com.example.spring.pago.response.TarjetaResponse;
import com.example.spring.pago.response.UsuarioDTO;

@Service
public class PagoServiceImpl implements PagoService {
	@Autowired
	BancoFeignClient bancoFeign;
	
	 @Autowired 
	 EventoFeignClient eventoFeign;
	 
	 @Autowired
	 UsuarioFeignClient usuarioFeign;
	 
	 @Autowired
	 UsuarioEventoRepository repo;
	
	public TarjetaResponse realizarPago(Tarjeta tarjeta, Long idUsuario, Long idEvento) {		
		UsuarioDTO usuarioDTO = usuarioFeign.getUsuario(idUsuario);
		EventoListadoDTO eventoListadoDTO = eventoFeign.getEvento(idEvento);
		
		repo.save(new UsuarioEvento(usuarioDTO.getId(), eventoListadoDTO.getId()));
		
		return bancoFeign.obtenerDatosValidacion(tarjeta);
	}
}
