package com.example.spring.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
