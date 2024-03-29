package com.example.spring.eventos.response;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.example.spring.eventos.utilidades.DeserializacionLocalDate;

public class EventoDTO {

	private Long id;

	@NotEmpty(message = "El nombre no puede ser vacío")
	private String nombre;

	@NotEmpty(message = "La descripción corta no puede ser vacía")
	private String descripcionCorta;

	private String descripcionExtendida;

	private String foto;

	@JsonDeserialize(using = DeserializacionLocalDate.class)
	@NotNull(message = "La fecha del evento no puede ser nula")
	private LocalDate fechaEvento;

	@NotNull(message = "La hora del evento no puede ser nula")
	private LocalTime horaEvento;

	@NotNull(message = "El precio mínimo no puede ser nulo")
	private String precioMinimo;

	@NotNull(message = "El precio máximo no puede ser nulo")
	private String precioMaximo;

	private String normas;

	@NotEmpty(message = "El nombre del recinto no puede ser vacío")
	private String recinto;

	public EventoDTO() {
		super();
	}

	public EventoDTO(Long id, String nombre, String descripcionCorta, String descripcionExtendida, String foto,
			LocalDate fechaEvento, LocalTime horaEvento, String precioMinimo, String precioMaximo, String normas,
			String recinto) {
		super();
		this.id = id;
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

	public EventoDTO(Evento evento, Recinto recinto) {
		this.id = evento.getIdEvento();
		this.nombre = evento.getNombre();
		this.descripcionCorta = evento.getDescripcionCorta();
		this.descripcionExtendida = evento.getDescripcionExtendida();
		this.foto = evento.getFoto();
		this.fechaEvento=evento.getFechaEvento();
		this.horaEvento = evento.getHoraEvento();
		this.precioMinimo = evento.getPrecioMinimo().toString();
		this.precioMaximo = evento.getPrecioMaximo().toString();
		this.normas = evento.getNormas();
		this.recinto = recinto.getNombre();
	}

	public Long getIdEvento() {
		return id;
	}

	public void setIdEvento(Long id) {
		this.id = id;
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

	public void setPrecioMinimo(String precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public Double getPrecioMaximo() {
		return convertirPrecioFormato(precioMaximo);
	}

	public Double getPrecioMinimo() {
		return convertirPrecioFormato(precioMinimo);
	}

	private Double convertirPrecioFormato(String precio) {
		if (precio == null) {
			return null;
		}

		String soloNumeros = precio.replaceAll("[^\\d.]", "");

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("#.0", symbols);

		return Double.parseDouble(formato.format(Double.parseDouble(soloNumeros)));
	}

	public void setPrecioMaximo(String precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public String getNormas() {
		return normas;
	}

	public void setNormas(String normas) {
		this.normas = normas;
	}

	public String getRecinto() {
		return recinto;
	}

	public void setRecinto(String recinto) {
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
