package br.com.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.dto.UsuarioDTO;
import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private RepositoryUsuario repositoryUsuario;

	@Override
	public UsuarioDTO listarUsuarios() {
		List<Usuario> usuarios = repositoryUsuario.findAll();
		return (UsuarioDTO) usuarios;
	}

}
