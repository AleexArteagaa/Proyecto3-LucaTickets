package com.example.spring.eventos.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.utilidades.DeserializacionLocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class EventoListadoDTO {

	private Long id;

	private String nombre;

	private String descripcionCorta;

	private String descripcionExtendida;

	private String foto;

	@JsonDeserialize(using = DeserializacionLocalDate.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fechaEvento;

	private LocalTime horaEvento;

	private String precioMinimo;

	private String precioMaximo;

	private String normas;

	private RecintoDTO recinto;

	public EventoListadoDTO() {
		super();
	}

	public EventoListadoDTO(Long id, String nombre, String descripcionCorta, String descripcionExtendida, String foto,
			LocalDate fechaEvento, LocalTime horaEvento, String precioMinimo, String precioMaximo, String normas,
			RecintoDTO recinto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcionCorta = descripcionCorta;
		this.descripcionExtendida = descripcionExtendida;
		this.foto = foto;
		this.fechaEvento=fechaEvento;
		this.horaEvento = horaEvento;
		this.precioMinimo = precioMinimo;
		this.precioMaximo = precioMaximo;
		this.normas = normas;
		this.recinto = recinto;
	}

	public EventoListadoDTO(Evento evento, Recinto recinto) {
		this.id = evento.getIdEvento();
		this.nombre = evento.getNombre();
		this.descripcionCorta = evento.getDescripcionCorta();
		this.descripcionExtendida = evento.getDescripcionExtendida();
		this.foto = evento.getFoto();
		this.fechaEvento = evento.getFechaEvento();
		this.horaEvento = evento.getHoraEvento();
		this.precioMinimo = evento.getPrecioMinimo().toString();
		this.precioMaximo = evento.getPrecioMaximo().toString();
		this.normas = evento.getNormas();
		this.recinto = new RecintoDTO(recinto);
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

	public String getPrecioMinimo() {
		return precioMinimo + " €";
	}

	public String getPrecioMaximo() {
		return precioMaximo + " €";
	}

	public void setPrecioMaximo(String precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public void setPrecioMinimo(String precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public String getNormas() {
		return normas;
	}

	public void setNormas(String normas) {
		this.normas = normas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RecintoDTO getRecinto() {
		return recinto;
	}

	public void setRecinto(RecintoDTO recinto) {
		this.recinto = recinto;
	}

	@Override
	public String toString() {
		return "EventoDTO [nombre=" + nombre + ", descripcionCorta=" + descripcionCorta + ", descripcionExtendida="
				+ descripcionExtendida + ", foto=" + foto + ", fechaEvento=" + fechaEvento + ", horaEvento="
				+ horaEvento + ", precioMinimo=" + precioMinimo + ", precioMaximo=" + precioMaximo + ", normas="
				+ normas + ", recinto=" + recinto + "]";
	}

}
