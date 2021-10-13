package br.com.apirest.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apirest.dto.UsuarioDTO;
import br.com.apirest.model.Telefone;
import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;
import br.com.apirest.service.ServiceRelatorio;
import br.com.apirest.service.TelefoneService;
import br.com.apirest.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private RepositoryUsuario repositoryUsuario;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TelefoneService telefoneService;
	
	@Autowired
	private ServiceRelatorio serviceRelatorio;

	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Retora ma lista de usuarios por ID")
	public ResponseEntity<UsuarioDTO> pesquisar(@PathVariable(value = "id") Long id) {
		Optional<Usuario> user = repositoryUsuario.findById(id);
		return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(user.get()), HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<?> pegarUsuario() {
		List<Usuario> usuarios = (List<Usuario>) repositoryUsuario.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Usuario> savarUsuario(@RequestBody Usuario usuario) {
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		String senhaCrypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCrypt);
		Usuario user = repositoryUsuario.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@PutMapping(produces = "json/application")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		Usuario userTemp = repositoryUsuario.findById(usuario.getId()).get();
		if (userTemp.getSenha().equals(usuario.getSenha())) {
			String senhaCrypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhaCrypt);
		}
		Usuario user = repositoryUsuario.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Long> deleteUser(@PathVariable Long id) {

		repositoryUsuario.deleteById(id);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	@GetMapping("/nome")
	public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
		List<Usuario> lista = repositoryUsuario.findUserByNome(nome);

		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("/fone/{id}")
	public ResponseEntity<?> deletartel(@PathVariable long id){
		telefoneService.deletarTel(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/relatorio", produces = "application/text")
	public ResponseEntity<String> downloadRelatorio(HttpServletRequest request) throws Exception{
		byte[] pdf = serviceRelatorio.gerarRelatorio("relatorio-usuario",request.getServletContext());
		
		String base64Pdf = "data:application/pdf;base64," + org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(pdf);
		
		return new ResponseEntity<String>(base64Pdf,HttpStatus.OK);
		
		
	}
	
}
