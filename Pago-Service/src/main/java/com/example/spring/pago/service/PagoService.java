package com.example.spring.pago.service;

import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;

public interface PagoService {
	public TarjetaResponse realizarPago(Tarjeta tarjeta, Long idUsuario, Long idEvento);
}
