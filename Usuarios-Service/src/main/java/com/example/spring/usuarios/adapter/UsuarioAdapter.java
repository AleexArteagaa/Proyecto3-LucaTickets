package com.example.spring.usuarios.adapter;

import org.springframework.stereotype.Component;

import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;

//Se utiliza para convertir de Entity a DTO
@Component
public class UsuarioAdapter {
	public UsuarioDTO of(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(usuario.getIdUsuario());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setContrasenia(usuario.getContrasenia());
        usuarioDTO.setFechaAlta(usuario.getFechaAlta());
        usuarioDTO.setMail(usuario.getMail());
        usuarioDTO.setNombre(usuario.getNombre());
        return usuarioDTO;
    }
}
