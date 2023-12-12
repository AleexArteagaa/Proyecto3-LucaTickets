package com.example.spring.eventos.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.eventos.controller.error.EventoNotFoundException;
import com.example.spring.eventos.controller.error.EventoRepetidoException;
import com.example.spring.eventos.controller.error.EventosIsEmptyException;
import com.example.spring.eventos.controller.error.InvalidYearException;
import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.repository.RepositoryEventos;

@Service
public class ServiceEventosImpl implements ServiceEventos {

	
	@Autowired
	private RepositoryEventos repository;
	
	@Override
	public Evento save(Evento evento) {

		List<Evento> eventos = repository.findAll();
		
		if (evento.getFechaEvento().getYear() < 2000) {
			throw new InvalidYearException();
		}
		
        for (Evento existingEvento : eventos) {
            if (existingEvento.equals(evento)) {
                throw new EventoRepetidoException();
            }
        }
		
		return repository.save(evento);
		
	}
	
	@Override
	public List<Evento> findAll() {
		
		List<Evento> eventos = repository.findAll();
				
	    if (eventos.isEmpty()) {
			throw new EventosIsEmptyException();
		}

		return eventos;
	}

	@Override
	public List<Evento> findByNombre(String name) {
		
	    List<Evento> eventos = repository.findByNombre(name).orElseThrow(EventoNotFoundException::new);
	    
	    if (eventos.isEmpty()) {
			throw new EventosIsEmptyException();
		}
	    
	    return eventos;

	}

	@Override
	public Evento findById(Long id) {
		Evento evento = repository.findById(id).orElseThrow(EventoNotFoundException::new);
    
		return evento;
		
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public List<Evento> findByGenero(String genero) {
		
	    List<Evento> eventos = repository.findByGenero(genero).orElseThrow(EventoNotFoundException::new);
	    
	    if (eventos.isEmpty()) {
			throw new EventosIsEmptyException();
		}
	    
	    return eventos;
	}

	@Override
	public List<Evento> findByCiudad(String ciudad) {
		
	    List<Evento> ciudades = repository.findByCiudad(ciudad).orElseThrow(EventoNotFoundException::new);
	    
	    if (ciudades.isEmpty()) {
			throw new EventosIsEmptyException();
		}
	    
	    return ciudades;
	}
	
	
}
