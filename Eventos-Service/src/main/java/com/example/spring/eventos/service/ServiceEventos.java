package com.example.spring.eventos.service;

import java.util.List;

import com.example.spring.eventos.model.Evento;

public interface ServiceEventos {

	List<Evento> findAll();

	List<Evento> findByNombre();

}
