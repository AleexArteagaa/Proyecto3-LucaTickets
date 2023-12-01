package com.example.spring.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	UsuarioRepository repo;
	
	public Usuario save(Usuario usuario) {
		return repo.save(usuario);
	}
}
