package com.example.spring.usuarios.service;

import java.util.List;

import com.example.spring.usuarios.model.Usuario;

public interface UsuarioService {

	public List<Usuario> findAll();

	public void deleteById(Long id);

	public Usuario save(Usuario usuario);

	public Usuario findById(Long id);
}
