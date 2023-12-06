package com.example.spring.pago.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.example.spring.pago.utilidades.ConversionMensajes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import feign.codec.DecodeException;

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
		// repo.save(new UsuarioEvento(usuarioAdapter.of(usuarioDTO),
		// eventoAdapter.of(eventoListadoDTO, recinto)));
		logger.info("--------- reliza el save de usuarioEvento");

		Token token = bancoFeign.getToken();
		TarjetaResponse response = new TarjetaResponse();
//		try {
			response = bancoFeign.obtenerDatosValidacion(token.getToken(), tarjeta);
			logger.info("--------- RESPONSE: "+response.toString());
			//response.setMessage("Entrada comprada con éxito");
			

//		} catch (FeignException.BadRequest e ) {
//			logger.info(e.getMessage());
//
//			String feignErrorMessage = e.contentUTF8();
//			ObjectMapper objectMapper = new ObjectMapper();
//			List<String> errorMessages = new ArrayList<>();
//			String status = "";
//			String error = "";
//			String infoAdicional = "";
//			String timeStamp = "";
//			
//
//			try {
//				JsonNode jsonNode = objectMapper.readTree(feignErrorMessage);
//				JsonNode messageNode = jsonNode.get("message");
//				JsonNode statusNode = jsonNode.get("status");
//				JsonNode timestampNode = jsonNode.get("timestamp");
//				JsonNode errorNode = jsonNode.get("error");
//				JsonNode infoAdicionalNode = jsonNode.get("infoAdicional");
//
//				if (statusNode != null) {
//					status = statusNode.asText();
//				}
//				
//				if (timestampNode != null) {
//					timeStamp = timestampNode.asText();
//				}
//				
//				if (errorNode != null) {
//					error = errorNode.asText();
//				}
//
//				if (infoAdicionalNode != null) {
//					infoAdicional = infoAdicionalNode.asText();
//				}
//
//				if (messageNode != null && messageNode.isArray()) {
//					for (final JsonNode objNode : messageNode) {
//						errorMessages.add(objNode.asText());
//					}
//				}
//				
//				
//			} catch (IOException ioException) {
//				logger.error("Error al parsear el mensaje de la excepción Feign: " + ioException.getMessage());
//			}
//			
//			response.setError(error);
//			response.setInfo(tarjeta);
//			response.setInfoAdicional(infoAdicional);
//			response.setStatus(status);
//			response.setTimestamp(timeStamp);
//			System.out.println(error + "dfsz");
//			if (error.equals("")) {
//				response.setMessage("Entrada comprada con éxito");
//			} else {
//				response.setMessage(ConversionMensajes.convertirError(error));
//			}
//			
//		}

		return response;

	}
}
