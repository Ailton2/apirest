package br.com.apirest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private RepositoryUsuario repositoryUsuario;
	

	
	@GetMapping(value = "/{id}" , produces = "application/json") 
	public ResponseEntity<Usuario> pesquisar(@PathVariable (value = "id") Long id){
		
	   Optional<Usuario> user = repositoryUsuario.findById(id);
		
		return new ResponseEntity(user.get(),HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> pegarUsuario(){
		
		List<Usuario> usuarios = (List<Usuario>) repositoryUsuario.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping(value = "/" ,produces = "application/json")
	public ResponseEntity<Usuario> savarUsuario(@RequestBody Usuario usuario){
		
		 Usuario user = repositoryUsuario.save(usuario);
		 
		 return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
	
	@PutMapping(value = "/" ,produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
		
		Usuario user =repositoryUsuario.save(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		
	}

	
}
