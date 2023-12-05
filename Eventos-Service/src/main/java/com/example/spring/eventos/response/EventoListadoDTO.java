package com.example.spring.eventos.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;


public class EventoListadoDTO {
	
	 private Long id;

    private String nombre;

    private String descripcionCorta;

    private String descripcionExtendida;

    private String foto;

    private LocalDate fechaEvento;

    private LocalTime horaEvento;

    private Double precioMinimo;

    private Double precioMaximo;

    private String normas;

    private RecintoDTO recinto;
    
		public EventoListadoDTO() {
			super();
		}

		public EventoListadoDTO(Long id, String nombre, String descripcionCorta, String descripcionExtendida, String foto,
				LocalDate fechaEvento, LocalTime horaEvento, Double precioMinimo, Double precioMaximo, String normas,
				RecintoDTO recinto) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.descripcionCorta = descripcionCorta;
			this.descripcionExtendida = descripcionExtendida;
			this.foto = foto;
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", new Locale("es", "ES"));
	        this.fechaEvento = LocalDate.parse(fechaEvento.format(formatter), formatter);
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", new Locale("es", "ES"));
	        this.fechaEvento = LocalDate.parse(evento.getFechaEvento().format(formatter), formatter);
			this.horaEvento = evento.getHoraEvento();
	        this.precioMinimo = evento.getPrecioMinimo();
	        this.precioMaximo = evento.getPrecioMaximo();
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

		public String getFechaEvento() {
			DateTimeFormatter formateada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			return fechaEvento.format(formateada);
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

	    public String getPrecioMaximo() {
	        return precioMaximo.toString()+ " €";
	    }
	    public String getPrecioMinimo() {
	    	return precioMinimo.toString() + " €";
	    }
	    
		public void setPrecioMaximo(Double precioMaximo) {
			this.precioMaximo = precioMaximo;
		}
		
		public void setPrecioMinimo(Double precioMinimo) {
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
