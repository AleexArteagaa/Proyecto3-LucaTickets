package com.example.spring.eventos.response;

import com.example.spring.eventos.model.Recinto;

public class RecintoDTO {
	
	private String nombre;
	private String localidad;
	
	

	public RecintoDTO() {
		super();

	}
	
	public RecintoDTO(String nombre, String localidad) {
		super();
		this.nombre = nombre;
		this.localidad = localidad;
	}
	
	public RecintoDTO(Recinto recinto) {
		super();
		this.nombre = recinto.getNombre();
		this.localidad = recinto.getCiudad();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	

}
