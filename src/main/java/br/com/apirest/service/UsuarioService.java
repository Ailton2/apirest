package br.com.apirest.service;

import java.util.List;

import br.com.apirest.dto.UsuarioDTO;
import br.com.apirest.model.Usuario;

public interface UsuarioService {
	
	UsuarioDTO listarUsuarios();
	List<Usuario> buscarUsuariosPorNome(String nome);

}
