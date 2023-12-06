package com.example.spring.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.response.EventoDTO;
import com.example.spring.eventos.response.EventoListadoDTO;
import com.example.spring.eventos.response.RecintoDTO;

import org.springframework.stereotype.Component;


@Component
public class EventoAdapter {
	
	public List<EventoListadoDTO> listaADTO(List<Evento> eventos) {

		List<EventoListadoDTO> eventosDTO = new ArrayList<EventoListadoDTO>();

		for (Evento evento : eventos) {
			eventosDTO.add(new EventoListadoDTO(evento.getIdEvento(), evento.getNombre(), evento.getDescripcionCorta(), evento.getDescripcionExtendida(), evento.getFoto(),
					evento.getFechaEvento(), evento.getHoraEvento(), evento.getPrecioMinimo().toString(), evento.getPrecioMaximo().toString(), evento.getNormas(), new RecintoDTO(evento.getRecinto())));
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
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo().toString());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo().toString());
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
    	eventoDTO.setPrecioMinimo(evento.getPrecioMinimo().toString());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo().toString());
    	eventoDTO.setNormas(evento.getNormas());
    	eventoDTO.setRecinto( new RecintoDTO(evento.getRecinto()));

        return eventoDTO;
    }

    public List<EventoListadoDTO> of(List<Evento> eventos) {

        return eventos.stream()
                .map(p -> of(p))
                .collect(Collectors.toList());
    }
}
