package com.example.spring.pago.service;

import com.example.spring.pago.response.TarjetaResponse;

public class PagoServiceImpl implements PagoService {
	public TarjetaResponse realizarPago() {
		return new TarjetaResponse();
	}
}
