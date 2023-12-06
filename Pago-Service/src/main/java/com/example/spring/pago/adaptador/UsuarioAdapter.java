package com.example.spring.pago.adaptador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.spring.pago.model.Usuario;
import com.example.spring.pago.response.UsuarioDTO;

@Component
public class UsuarioAdapter {
	public List<UsuarioDTO> listaADTO(List<Usuario> usuarios) {

		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();

		for (Usuario usuario : usuarios) {
			usuariosDTO.add(new UsuarioDTO(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
					usuario.getMail(), usuario.getContrasenia(), usuario.getFechaAlta()));
		}

		return usuariosDTO;
	}

//	public UsuarioDTO of(Usuario usuario) {
//		UsuarioDTO usuarioDTO = new UsuarioDTO();
//		usuarioDTO.setId(usuario.getIdUsuario());
//		usuarioDTO.setApellido(usuario.getApellido());
//		usuarioDTO.setContrasenia(usuario.getContrasenia());
//		usuarioDTO.setFechaAlta(usuario.getFechaAlta2());
//		usuarioDTO.setMail(usuario.getMail());
//		usuarioDTO.setNombre(usuario.getNombre());
//		return usuarioDTO;
//	}
//	
	public Usuario of(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(usuarioDTO.getId());
		usuario.setApellido(usuarioDTO.getApellido());
		usuario.setContrasenia(usuarioDTO.getContrasenia());
		usuario.setFechaAlta(usuarioDTO.getFechaAlta());
		usuario.setMail(usuarioDTO.getMail());
		usuario.setNombre(usuarioDTO.getNombre());
		return usuario;
	}
}
