package com.example.spring.eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.eventos.model.Evento;


public interface RepositoryEventos extends JpaRepository<Evento, Long> {

<<<<<<< HEAD
	public Optional <List<Evento>> findByNombre(String nombre);

=======
	public List<Evento> findByNombre(String nombre);
>>>>>>> c8d7070be3f47c809470f149b56cd47d9a559226
	
}
	

