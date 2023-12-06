package com.example.spring.pago.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.pago.adaptador.EventoAdapter;
import com.example.spring.pago.adaptador.UsuarioAdapter;
import com.example.spring.pago.controller.PagoController;
import com.example.spring.pago.feignclients.BancoFeignClient;
import com.example.spring.pago.feignclients.EventoFeignClient;
import com.example.spring.pago.feignclients.UsuarioFeignClient;
import com.example.spring.pago.model.Recinto;
import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.model.Token;
import com.example.spring.pago.model.UsuarioEvento;
import com.example.spring.pago.repository.UsuarioEventoRepository;
import com.example.spring.pago.response.EventoListadoDTO;
import com.example.spring.pago.response.TarjetaResponse;
import com.example.spring.pago.response.UsuarioDTO;

import feign.FeignException;

@Service
public class PagoServiceImpl implements PagoService {
	private static final Logger logger = LoggerFactory.getLogger(PagoServiceImpl.class);

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
			logger.info("--------- entra en realizar pago");
			UsuarioDTO usuarioDTO = usuarioFeign.getUsuario(idUsuario);
			logger.info("--------- reliza el feign client de usuario");
			EventoListadoDTO eventoListadoDTO = eventoFeign.getEvento(idEvento);
			logger.info("--------- reliza el feign client de evento");
			Recinto recinto = eventoFeign.getRecinto(eventoListadoDTO.getRecinto().getNombre());
			logger.info("--------- reliza el feign client de recinto");
			//repo.save(new UsuarioEvento(usuarioAdapter.of(usuarioDTO), eventoAdapter.of(eventoListadoDTO, recinto)));
			logger.info("--------- reliza el save de usuarioEvento");
			
			Token token = bancoFeign.getToken();
			TarjetaResponse response = new TarjetaResponse();
			//try {
				response = bancoFeign.obtenerDatosValidacion(token.getToken(),tarjeta);
				logger.info(response.toString());

//			} catch (FeignException e) {
//				logger.info(e.getMessage());
//				response.setInfo(tarjeta);
//			}
			
			

			
			return response;
		
	 }
}
