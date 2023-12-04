package com.example.spring.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.spring.eventos.controller.error.EventoNotFoundException;
import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.response.EventoDTO;

import org.springframework.stereotype.Component;


@Component
public class EventoAdapter {
	
    public EventoDTO of(Evento evento) {
    	EventoDTO eventoDTO = new EventoDTO();
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNombre());

        return eventoDTO;
    }

    public EventoDTO of(Evento evento, Recinto recinto) {
    	EventoDTO eventoDTO = new EventoDTO();
    	eventoDTO.setNombre(evento.getNombre());
    	eventoDTO.setDescripcionCorta(evento.getDescripcionCorta());
    	eventoDTO.setDescripcionExtendida(evento.getDescripcionExtendida());
    	eventoDTO.setFoto(evento.getFoto());
    	eventoDTO.setFechaEvento(evento.getFechaEvento());
    	eventoDTO.setHoraEvento(evento.getHoraEvento());
    	eventoDTO.setPrecioMaximo(evento.getPrecioMaximo());
    	eventoDTO.setNormas(evento.getNombre());
    	eventoDTO.setRecinto(recinto.getNombre());

        return eventoDTO;
    }

    public List<EventoDTO> of(List<Evento> eventos) {

        return eventos.stream()
                .map(p -> of(p))
                .collect(Collectors.toList());
    }

    public List<EventoDTO> of(Optional<List<Evento>> optionalEventos) {
        if (optionalEventos.isPresent()) {
            return optionalEventos.get().stream()
                                   .map(this::of)
                                   .collect(Collectors.toList());
        } else {
        	 throw new EventoNotFoundException();
        }
    }
}
