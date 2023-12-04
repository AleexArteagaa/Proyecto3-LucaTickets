package com.example.spring.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.response.EventoDTO;

import org.springframework.stereotype.Component;


@Component
public class EventoAdapter {
	
	public List<EventoDTO> listaADTO(List<Evento> eventos) {

		List<EventoDTO> eventosDTO = new ArrayList<EventoDTO>();

		for (Evento evento : eventos) {
			eventosDTO.add(new EventoDTO(evento.getIdEvento(), evento.getNombre(), evento.getDescripcionCorta(), evento.getDescripcionExtendida(), evento.getFoto(),
					evento.getFechaEvento(), evento.getHoraEvento(), evento.getPrecioMinimo(), evento.getPrecioMaximo(), evento.getNormas(), evento.getRecinto().getNombre()));
		}

		return eventosDTO;
	}
	
    public EventoDTO of(Evento evento) {
    	EventoDTO eventoDTO = new EventoDTO();
    	eventoDTO.setIdEvento(evento.getIdEvento());
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNormas());
    	eventoDTO.setRecinto(evento.getRecinto().getNombre());

        return eventoDTO;
    }
    
    public EventoDTO of(Evento evento, Recinto recinto) {
    	EventoDTO eventoDTO = new EventoDTO();
    	eventoDTO.setIdEvento(evento.getIdEvento());
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNormas());
    	eventoDTO.setRecinto(recinto.getNombre());

        return eventoDTO;
    }

    public List<EventoDTO> of(List<Evento> eventos) {

        return eventos.stream()
                .map(p -> of(p))
                .collect(Collectors.toList());
    }
}
