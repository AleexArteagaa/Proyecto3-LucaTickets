package com.example.spring.eventos.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.repository.RepositoryEventos;

@Service
public class ServiceEventosImpl implements ServiceEventos {

	
	@Autowired
	private RepositoryEventos repository;
	
	@Override
	public Evento save(Evento evento) {
		return repository.save(evento);
		
	}
	
	@Override
	public List<Evento> findAll() {

		return repository.findAll();
	}

	@Override
	public List<Evento> findByNombre() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
