package com.example.spring.pago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.pago.adaptador.EventoAdapter;
import com.example.spring.pago.adaptador.UsuarioAdapter;
import com.example.spring.pago.feignclients.BancoFeignClient;
import com.example.spring.pago.feignclients.EventoFeignClient;
import com.example.spring.pago.feignclients.UsuarioFeignClient;
import com.example.spring.pago.model.Recinto;
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
	 
	 @Autowired
	 UsuarioAdapter usuarioAdapter;
	 
	 @Autowired
	 EventoAdapter eventoAdapter;
	 	 
	
	public TarjetaResponse realizarPago(Tarjeta tarjeta, Long idUsuario, Long idEvento) {		
		UsuarioDTO usuarioDTO = usuarioFeign.getUsuario(idUsuario);
		EventoListadoDTO eventoListadoDTO = eventoFeign.getEvento(idEvento);
		Recinto recinto = eventoFeign.getRecinto(eventoListadoDTO.getRecinto().getNombre());
		repo.save(new UsuarioEvento(usuarioAdapter.of(usuarioDTO), eventoAdapter.of(eventoListadoDTO, recinto)));
		
		return bancoFeign.obtenerDatosValidacion(tarjeta);
	}
}
