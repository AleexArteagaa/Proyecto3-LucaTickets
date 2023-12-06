package com.example.spring.pago.utilidades;

public class ConversionMensajes {
	public static String convertirError(String error) {
		String codigoError = error.substring(0, 8);
		String mensajeError = "";

		switch (codigoError) {
		case "400.0001":
			mensajeError = "No hay fondos suficientes en la cuenta";
			break;
		case "400.0002":
			mensajeError = "No se encuentran los datos del cliente";
			break;
		case "400.0003":
			mensajeError = "El número de la tarjeta no es válido";
			break;
		case "400.0004":
			mensajeError = "El formato del CVV no es válido";
			break;
		case "400.0005":
			mensajeError = "El mes de caducidad de la tarjeta no es correcto";
			break;
		case "400.0006":
			mensajeError = "El año de caducidad de la tarjeta no es correcto";
			break;
		case "400.0007":
			mensajeError = "La fecha de caducidad es anterior al día actual";
			break;
		case "400.0008":
			mensajeError = "El formato del nombre no es correcto";
			break;
		case "500.0001":
			mensajeError = "El sistema se encuentra inestable";
			break;
		}

		return mensajeError;
	}
}
