package com.example.spring.eventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.eventos.controller.error.RecintoIsNullException;
import com.example.spring.eventos.controller.error.RecintoNotFoundException;
import com.example.spring.eventos.model.Recinto;
import com.example.spring.eventos.repository.RepositoryRecinto;

@Service
public class ServiceRecintoImpl implements ServiceRecinto{
	
	@Autowired
	private RepositoryRecinto repository;

	@Override
	public Recinto obtenerPorNombre(String nombre) {

		if (nombre != null) {
			 return repository.findByNombre(nombre)
		                .orElseThrow(() -> new RecintoNotFoundException());
		}else {
			throw new RecintoIsNullException();
		}
		
	}

}
