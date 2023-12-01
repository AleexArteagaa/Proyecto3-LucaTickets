package com.example.spring.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recintos")
public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recinto")
    private Long idRecinto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "tipo_recinto", nullable = false)
    private String tipoRecinto;

    @Column(name = "aforo", nullable = false)
    private int aforo;
   
    public Recinto() {
		super();
	}

	public Recinto(String nombre, String ciudad, String direccion, String tipoRecinto, int aforo) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipoRecinto = tipoRecinto;
        this.aforo = aforo;
    }

	public Long getIdRecinto() {
		return idRecinto;
	}

	public void setIdRecinto(Long idRecinto) {
		this.idRecinto = idRecinto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipoRecinto() {
		return tipoRecinto;
	}

	public void setTipoRecinto(String tipoRecinto) {
		this.tipoRecinto = tipoRecinto;
	}

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	@Override
	public String toString() {
		return "Recinto [idRecinto=" + idRecinto + ", nombre=" + nombre + ", ciudad=" + ciudad + ", dirección="
				+ direccion + ", tipoRecinto=" + tipoRecinto + ", aforo=" + aforo + "]";
	}
	
	
}