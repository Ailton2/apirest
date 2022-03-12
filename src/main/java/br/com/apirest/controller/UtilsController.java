package br.com.apirest.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.apirest.exception.ObjetoErro;
import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;
import br.com.apirest.service.ServiceEnviarEmail;
import br.com.apirest.service.UsuarioService;

@RestController
@RequestMapping(value = "/utils")
public class UtilsController {

	@Autowired
	private RepositoryUsuario repositoryUsuario;
	
	@Autowired
	private ServiceEnviarEmail serviceEnviarEmail;

	@ResponseBody
	@PostMapping("/recuperar")
	public ResponseEntity<ObjetoErro> recuperar(@RequestBody Usuario login) throws Exception {

		ObjetoErro objetoErro = new ObjetoErro();
		
		Usuario user = repositoryUsuario.findUserByLogin(login.getLogin());

		if (user == null) {
			objetoErro.setCode("404");
			objetoErro.setError("Usuário não encontrado");
		}else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String senhaNova = dateFormat.format(Calendar.getInstance().getTime());
			String senhaCript = new BCryptPasswordEncoder().encode(senhaNova);
			
			repositoryUsuario.updateSenha(senhaCript, user.getId());

			serviceEnviarEmail.enviarEmail("Recuperar senha", user.getLogin(), "Sua senha nova é : " +senhaNova);

			objetoErro.setCode("200");
			objetoErro.setError("Acesso enviado por email");
		}
		return new ResponseEntity<ObjetoErro>(objetoErro, HttpStatus.OK);
	}

}
