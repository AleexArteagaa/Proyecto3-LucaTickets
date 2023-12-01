package com.example.spring.eventos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.eventos.model.Recinto;



public interface RepositoryRecinto extends JpaRepository<Recinto, Long>{
	  Optional<Recinto> findByNombre(String nombre);
}
