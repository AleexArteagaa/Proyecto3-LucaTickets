package com.example.spring.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.eventos.model.Evento;



public interface RepositoryEventos extends JpaRepository<Evento, Long> {
	
}
	

