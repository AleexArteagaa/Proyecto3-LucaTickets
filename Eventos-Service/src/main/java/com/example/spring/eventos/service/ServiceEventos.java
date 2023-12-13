package com.example.spring.eventos.service;

import java.util.List;

import com.example.spring.eventos.model.Evento;

public interface ServiceEventos {

	public Evento save(Evento evento);

	public List<Evento> findAll();

	public List<Evento> findByNombre(String name);

	public Evento findById(Long id);
	
	public List<Evento> findByGenero(String genero);
	
	public List<Evento> findByCiudad(String ciudad);
	
}
