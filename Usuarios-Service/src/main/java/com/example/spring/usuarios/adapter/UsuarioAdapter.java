package com.example.spring.usuarios.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.spring.usuarios.model.Usuario;
import com.example.spring.usuarios.response.UsuarioDTO;

//Se utiliza para convertir de Entity a DTO
@Component
public class UsuarioAdapter {

	public List<UsuarioDTO> listaADTO(List<Usuario> usuarios) {

		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();

		for (Usuario usuario : usuarios) {
			usuariosDTO.add(new UsuarioDTO(usuario.getIdUsuario(),usuario.getNombre(), usuario.getApellido(), usuario.getMail(),
					usuario.getContrasenia(), usuario.getFechaAlta()));
		}

		return usuariosDTO;
	}

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

	public UsuarioDTO of(Optional<Usuario> usuario) {
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(usuario.get().getIdUsuario());
		usuarioDTO.setNombre(usuario.get().getNombre());
		usuarioDTO.setApellido(usuario.get().getApellido());
		usuarioDTO.setContrasenia(usuario.get().getContrasenia());
		usuarioDTO.setFechaAlta(usuario.get().getFechaAlta());
		usuarioDTO.setMail(usuario.get().getMail());
		
		return usuarioDTO;
	}
}
