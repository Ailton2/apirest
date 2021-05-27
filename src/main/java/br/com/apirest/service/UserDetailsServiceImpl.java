package br.com.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private RepositoryUsuario usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//consultar no banco
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario nao encontrado");
		}
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}

}
