package com.example.spring.usuarios.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.example.spring.usuarios.utilidades.DeserializacionLocalDate;

public class UsuarioDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private String mail;
	private String contrasenia;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fechaAlta;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Long id, String nombre, String apellido, String mail, String contrasenia, LocalDate fechaAlta) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.contrasenia = contrasenia;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", new Locale("es", "ES"));
        this.fechaAlta = LocalDate.parse(fechaAlta.format(formatter), formatter);
	}
	
	public UsuarioDTO(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaAlta) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.contrasenia = contrasenia;
		this.fechaAlta = fechaAlta;
	}

	public UsuarioDTO(String nombre, String apellido, String mail, String contrasenia, String fechaFormateada) {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public  LocalDate getFechaAlta() {		
		
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
		return "UsuarioResponse [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail
				+ ", contrasenia=" + contrasenia + ", fechaAlta=" + fechaAlta + "]";
	}

}
