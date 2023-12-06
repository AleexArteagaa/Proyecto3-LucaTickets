package com.example.spring.pago.response;

import com.example.spring.pago.model.Tarjeta;

public class TarjetaResponse {

	private String timestamp;

	private String status;

	private String error;

	private String message;

	private Tarjeta info;

	private String infoAdicional;

	public TarjetaResponse() {
		super();
	}

	public TarjetaResponse(String timestamp, String status, String error, String message, Tarjeta info,
			String infoAdicional) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.info = info;
		this.infoAdicional = infoAdicional;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Tarjeta getInfo() {
		return info;
	}

	public void setInfo(Tarjeta info) {
		this.info = info;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	@Override
	public String toString() {
		return "TarjetaResponse [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", message="
				+ message + ", info=" + info + ", infoAdicional=" + infoAdicional + "]";
	}

}
