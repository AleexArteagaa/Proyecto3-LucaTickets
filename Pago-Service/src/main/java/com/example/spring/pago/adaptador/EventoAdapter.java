package com.example.spring.pago.adaptador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.spring.pago.model.Evento;
import com.example.spring.pago.model.Recinto;
import com.example.spring.pago.response.EventoListadoDTO;
import com.example.spring.pago.response.RecintoDTO;


@Component
public class EventoAdapter {
	
	public List<EventoListadoDTO> listaADTO(List<Evento> eventos) {

		List<EventoListadoDTO> eventosDTO = new ArrayList<EventoListadoDTO>();

		for (Evento evento : eventos) {
			eventosDTO.add(new EventoListadoDTO(evento.getIdEvento(), evento.getNombre(), evento.getDescripcionCorta(), evento.getDescripcionExtendida(), evento.getFoto(),
					evento.getFechaEvento(), evento.getHoraEvento(), evento.getPrecioMinimo(), evento.getPrecioMaximo(), evento.getNormas(), new RecintoDTO(evento.getRecinto())));
		}

		return eventosDTO;
	}
	
    public EventoListadoDTO of(Evento evento) {
    	EventoListadoDTO eventoDTO = new EventoListadoDTO();
    	eventoDTO.setId(evento.getIdEvento());
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNormas());
    	eventoDTO.setRecinto( new RecintoDTO(evento.getRecinto()));

        return eventoDTO;
    }
    
    public EventoListadoDTO of(Evento evento, Recinto recinto) {
    	EventoListadoDTO eventoDTO = new EventoListadoDTO();
    	eventoDTO.setId(evento.getIdEvento());
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNormas());
    	eventoDTO.setRecinto( new RecintoDTO(evento.getRecinto()));

        return eventoDTO;
    }
    
    public Evento of(EventoListadoDTO eventoDTO, Recinto recinto) {
    	Evento evento = new Evento();
    	evento.setIdEvento(eventoDTO.getId());
    	evento.setNombre(eventoDTO.getNombre());
    	evento.setDescripcionCorta(eventoDTO.getDescripcionCorta());
    	evento.setDescripcionExtendida(eventoDTO.getDescripcionExtendida());
    	evento.setFoto(eventoDTO.getFoto());
    	evento.setFechaEvento(eventoDTO.getFechaEvento());
    	evento.setHoraEvento(eventoDTO.getHoraEvento());
    	evento.setPrecioMinimo(eventoDTO.getPrecioMinimo());
    	evento.setPrecioMaximo(eventoDTO.getPrecioMaximo());
    	evento.setNormas(eventoDTO.getNormas());
    	evento.setRecinto(recinto);

        return evento;
    }

    public List<EventoListadoDTO> of(List<Evento> eventos) {

        return eventos.stream()
                .map(p -> of(p))
                .collect(Collectors.toList());
    }
}
