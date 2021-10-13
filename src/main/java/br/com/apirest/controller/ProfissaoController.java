package br.com.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apirest.model.Profissao;
import br.com.apirest.repository.RepositoryProfissao;

@RestController
@RequestMapping(value = "/profissao")
public class ProfissaoController {
	
	@Autowired
	private RepositoryProfissao repositoryProfissao;
	
	@GetMapping(value = "/list")
	public ResponseEntity<?> buscarProfissoes(){
		
	List<Profissao> response = repositoryProfissao.findAll();
		return ResponseEntity.ok(response);
	}

}
