package com.example.spring.eventos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.spring.eventos.controller.error.RecintoNotFoundException;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.repository.RepositoryRecinto;

public class ServiceRecintoImpl implements ServiceRecinto{
	
	@Autowired
	private RepositoryRecinto repository;

	@Override
	public Recinto obtenerPorNombre(String nombre) {
		 return repository.findByNombre(nombre)
	                .orElseThrow(() -> new RecintoNotFoundException());
	}

}
