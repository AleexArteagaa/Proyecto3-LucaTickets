package com.example.spring.eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring.eventos.model.Evento;


public interface RepositoryEventos extends JpaRepository<Evento, Long> {

	@Query("SELECT e FROM Evento e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
	public Optional <List<Evento>> findByNombre(String nombre);

	@Query("SELECT e FROM Evento e WHERE LOWER(e.descripcionCorta) LIKE LOWER(CONCAT('%', :genero, '%')) OR LOWER(e.descripcionExtendida) LIKE LOWER(CONCAT('%', :genero, '%'))")
	public Optional<List<Evento>> findByGenero(String genero);
	
	@Query("SELECT e FROM Evento e JOIN e.recinto r WHERE LOWER(r.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%'))")
	public Optional<List<Evento>> findByCiudad(String ciudad);

}
	

