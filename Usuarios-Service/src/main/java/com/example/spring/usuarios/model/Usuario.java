package com.example.spring.usuarios.model;

import java.time.LocalDate;

import com.example.spring.usuarios.utilidades.DeserializacionLocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

    @Column(name = "nombre")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @Column(name = "apellido")
    @NotEmpty(message = "El apellido no puede estar vacío")
    private String apellido;

    @Column(name = "mail")
    @NotEmpty(message = "El mail no puede estar vacío")
    @Email(message = "El formato del mail es incorrecto")
    private String mail;

    @Column(name = "contrasenia")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String contrasenia;

    @JsonDeserialize(using = DeserializacionLocalDate.class)
    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

	public Usuario() {
		super();
	}

    public Usuario(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaAlta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.fechaAlta = fechaAlta;
    }
    
    public Usuario(Long idUsuario, String nombre, String apellido, String mail, String contrasenia, LocalDate fechaAlta) {
    	this.idUsuario=idUsuario;
    	this.nombre = nombre;
    	this.apellido = apellido;
    	this.mail = mail;
    	this.contrasenia = contrasenia;
    	this.fechaAlta = fechaAlta;
    }

    public Usuario(String nombre, String apellido, String mail, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.fechaAlta = LocalDate.now();
    }

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail
				+ ", contraseña=" + contrasenia + ", fechaAlta=" + fechaAlta + "]";
	}

}
