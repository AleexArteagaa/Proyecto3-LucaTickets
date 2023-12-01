package com.example.spring.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Recintos")
public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recinto")
    private Long idRecinto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "dirección", nullable = false)
    private String dirección;

    @Column(name = "tipoRecinto", nullable = false)
    private String tipoRecinto;

    @Column(name = "aforo", nullable = false)
    private int aforo;
   
    public Recinto() {
		super();
	}

	public Recinto(String nombre, String ciudad, String dirección, String tipoRecinto, int aforo) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.dirección = dirección;
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

	public String getDirección() {
		return dirección;
	}

	public void setDirección(String dirección) {
		this.dirección = dirección;
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
				+ dirección + ", tipoRecinto=" + tipoRecinto + ", aforo=" + aforo + "]";
	}
	
	
}