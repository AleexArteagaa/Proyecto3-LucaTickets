package com.example.spring.usuarios.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion_corta", nullable = false)
    private String descripcionCorta;

    @Column(name = "descripcion_extendida", columnDefinition = "TEXT")
    private String descripcionExtendida;

    @Column(name = "foto")
    private String foto;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "hora_evento", nullable = false)
    private LocalTime horaEvento;

    @Column(name = "precio_minimo", nullable = false)
    private Double precioMinimo;

    @Column(name = "precio_maximo", nullable = false)
    private Double precioMaximo;

    @Column(name = "normas")
    private String normas;

    @ManyToOne
    @JoinColumn(name = "id_recinto", nullable = false)
    private Recinto recinto;
    
    public Evento() {
		super();
	}


	public Evento(String nombre, String descripcionCorta, String descripcionExtendida, String foto,
                  LocalDate fechaEvento, LocalTime horaEvento, Double precioMinimo, Double precioMaximo,
                  String normas, Recinto recinto) {
        this.nombre = nombre;
        this.descripcionCorta = descripcionCorta;
        this.descripcionExtendida = descripcionExtendida;
        this.foto = foto;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.normas = normas;
        this.recinto = recinto;
    }


	public Long getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcionCorta() {
		return descripcionCorta;
	}


	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}


	public String getDescripcionExtendida() {
		return descripcionExtendida;
	}


	public void setDescripcionExtendida(String descripcionExtendida) {
		this.descripcionExtendida = descripcionExtendida;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public LocalDate getFechaEvento() {
		return fechaEvento;
	}


	public void setFechaEvento(LocalDate fechaEvento) {
		this.fechaEvento = fechaEvento;
	}


	public LocalTime getHoraEvento() {
		return horaEvento;
	}


	public void setHoraEvento(LocalTime horaEvento) {
		this.horaEvento = horaEvento;
	}


	public Double getPrecioMinimo() {
		return precioMinimo;
	}


	public void setPrecioMinimo(Double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}


	public Double getPrecioMaximo() {
		return precioMaximo;
	}


	public void setPrecioMaximo(Double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}


	public String getNormas() {
		return normas;
	}


	public void setNormas(String normas) {
		this.normas = normas;
	}


	public Recinto getRecinto() {
		return recinto;
	}


	public void setRecinto(Recinto recinto) {
		this.recinto = recinto;
	}


	@Override
	public String toString() {
		return "Evento [idEvento=" + idEvento + ", nombre=" + nombre + ", descripcionCorta=" + descripcionCorta
				+ ", descripcionExtendida=" + descripcionExtendida + ", foto=" + foto + ", fechaEvento=" + fechaEvento
				+ ", horaEvento=" + horaEvento + ", precioMinimo=" + precioMinimo + ", precioMaximo=" + precioMaximo
				+ ", normas=" + normas + ", recinto=" + recinto + "]";
	}
	
	
}