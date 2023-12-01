package com.example.spring.eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.eventos.model.Evento;


public interface RepositoryEventos extends JpaRepository<Evento, Long> {

	public Optional <List<Evento>> findByNombre(String nombre);

	
}
	

