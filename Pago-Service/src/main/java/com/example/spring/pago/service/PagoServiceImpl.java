package com.example.spring.pago.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import jakarta.transaction.Transactional;

@Transactional
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

		EventoListadoDTO eventoListadoDTO = new EventoListadoDTO();
		Recinto recinto = new Recinto();
		
		Token token = bancoFeign.getToken();
		TarjetaResponse response = new TarjetaResponse();
		
		try {
			eventoListadoDTO = eventoFeign.getEvento(idEvento);
			logger.info("--------- reliza el feign client de evento");
			recinto = eventoFeign.getRecinto(eventoListadoDTO.getRecinto().getNombre());
			logger.info("--------- reliza el feign client de recinto");

		} catch (FeignException e) {
			String feignErrorMessage = e.contentUTF8();
			ObjectMapper objectMapper = new ObjectMapper();
			List<String> errorMessages = new ArrayList<>();
			String status = "";
			String error = "";
			String timeStamp = "";

			try {
				JsonNode jsonNode = objectMapper.readTree(feignErrorMessage);
				JsonNode messageNode = jsonNode.get("message");
				JsonNode statusNode = jsonNode.get("status");
				JsonNode timestampNode = jsonNode.get("timestamp");
				JsonNode errorNode = jsonNode.get("error");

				if (statusNode != null) {
					status = statusNode.asText();
				}

				if (timestampNode != null) {
					timeStamp = timestampNode.asText();
				}

				if (errorNode != null) {
					error = errorNode.asText();
				}

				if (messageNode != null && messageNode.isArray()) {
					for (final JsonNode objNode : messageNode) {
						errorMessages.add(objNode.asText());
					}
				}

			} catch (IOException ioException) {
				logger.error("Error al parsear el mensaje de la excepción Feign: " + ioException.getMessage());
			}

			response.setError(error);
			response.setInfo(tarjeta);
			response.setInfoAdicional("");
			response.setStatus(status);
			response.setTimestamp(timeStamp);
			response.setMessage(errorMessages.get(0));
			
			return response;

		}

		try {
			response = bancoFeign.obtenerDatosValidacion(token.getToken(), tarjeta);

		} catch (FeignException e) {
			if (e.status() == 400) {
				String feignErrorMessage = e.contentUTF8();
				ObjectMapper objectMapper = new ObjectMapper();
				List<String> errorMessages = new ArrayList<>();
				String status = "";
				String error = "";
				String timeStamp = "";

				try {
					JsonNode jsonNode = objectMapper.readTree(feignErrorMessage);
					JsonNode messageNode = jsonNode.get("message");
					JsonNode statusNode = jsonNode.get("status");
					JsonNode timestampNode = jsonNode.get("timestamp");
					JsonNode errorNode = jsonNode.get("error");

					if (statusNode != null) {
						status = statusNode.asText();
					}

					if (timestampNode != null) {
						timeStamp = timestampNode.asText();
					}

					if (errorNode != null) {
						error = errorNode.asText();
					}

					if (messageNode != null && messageNode.isArray()) {
						for (final JsonNode objNode : messageNode) {
							errorMessages.add(objNode.asText());
						}
					}

				} catch (IOException ioException) {
					logger.error("Error al parsear el mensaje de la excepción Feign: " + ioException.getMessage());
				}

				response.setError(error);
				response.setInfo(tarjeta);
				if (ConversionMensajes.convertirError(error).equals("")) {
					response.setInfoAdicional(
							"Revisa el formato de los campos. Posiblemente te hayas equivocado en el tipo y sea numérico o te hayas equivocado en el nombre de un dato de entrada o directamente te falte una información de entrada");
				} else {
					response.setInfoAdicional("");
				}
				response.setStatus(status);
				response.setTimestamp(timeStamp);
				response.setMessage(ConversionMensajes.convertirError(error));

			} else {
				LocalDateTime ahora = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				String timestampFormateado = ahora.format(formatter);

				response.setError("Transacción correcta");
				response.setStatus("200.0001");
				response.setTimestamp(timestampFormateado);
				response.setMessage("Entrada comprada con éxito");
				response.setInfoAdicional("");
				response.setInfo(tarjeta);

				repo.save(
						new UsuarioEvento(usuarioAdapter.of(usuarioDTO), eventoAdapter.of(eventoListadoDTO, recinto)));
				logger.info("--------- reliza el save de usuarioEvento");
			}

		}

		return response;

	}
}
