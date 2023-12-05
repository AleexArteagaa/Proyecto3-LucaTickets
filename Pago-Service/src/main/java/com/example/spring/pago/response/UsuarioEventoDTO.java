package com.example.spring.pago.response;

public class UsuarioEventoDTO {
	
	private String usuario;
	
	private String evento;

	public UsuarioEventoDTO() {
		super();
	}

	public UsuarioEventoDTO(String usuario, String evento) {
		super();
		this.usuario = usuario;
		this.evento = evento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "UsuarioEventoDTO [usuario=" + usuario + ", evento=" + evento + "]";
	}
	
	
	
	

}
