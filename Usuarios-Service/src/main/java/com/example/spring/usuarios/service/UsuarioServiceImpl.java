package com.example.spring.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.usuarios.controller.error.ListEmptyException;
import com.example.spring.usuarios.controller.error.UsuarioNotFoundException;
import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	UsuarioRepository repo;

	@Override
	public List<Usuario> findAll() {

		List<Usuario> usuarios = repo.findAll();

		if (usuarios.isEmpty()) {
			throw new ListEmptyException();
		}

		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return repo.save(usuario);
	}

	@Override
	public Usuario findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
	}

}
