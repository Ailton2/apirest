package br.com.apirest.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.apirest.dao.UsuarioDao;
import br.com.apirest.dto.UsuarioDTO;
import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;
import br.com.apirest.service.ServiceRelatorio;
import br.com.apirest.service.TelefoneService;
import br.com.apirest.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RepositoryUsuario repositoryUsuario;
	
	@Autowired
	private TelefoneService telefoneService;
	
	@Autowired
	private ServiceRelatorio serviceRelatorio;

	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Retorna um usuario por ID")
	public ResponseEntity<UsuarioDTO> pesquisar(@PathVariable(value = "id") Long id) {
	    Usuario user = usuarioDao.buscarUsuarioPorId(id);
		return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(user), HttpStatus.OK);

	}

	@GetMapping
	@ApiOperation(value = "Retorna uma lista de usuarios")
	public ResponseEntity<?> listUsuarios() {
		List<Usuario> usuarios = (List<Usuario>) repositoryUsuario.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Salva um usuario")
	public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		String senhaCrypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCrypt);
		Usuario user = repositoryUsuario.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@PutMapping(produces = "json/application")
	public ResponseEntity<Usuario> updateUsuarios(@RequestBody Usuario usuario) {

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
		List<Usuario> lista = usuarioService.buscarUsuariosPorNome(nome);
		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("/fone/{id}")
	public ResponseEntity<?> deletartel(@PathVariable long id){
		telefoneService.deletarTel(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/relatorio", produces = "application/text")
	public ResponseEntity<String> downloadRelatorio(HttpServletRequest request) throws Exception{
		byte[] pdf = serviceRelatorio.gerarRelatorio("relatorio-usuario",new HashMap<>(),request.getServletContext());
		
		String base64Pdf = "data:application/pdf;base64," + org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(pdf);
		
		return new ResponseEntity<String>(base64Pdf,HttpStatus.OK);
			
	}
	
	@GetMapping(value = "/relatorio/params", produces = "application/text")
	public ResponseEntity<String> downloadRelatorioParams(HttpServletRequest request ,
			                  @RequestParam String dataInicio,
			                  @RequestParam String dataFim ) throws Exception{
		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatParams = new SimpleDateFormat("yyyy-MM-dd");
		
		String dataIni = dateFormatParams.format(dtFormat.parse(dataInicio));
		String dateF = dateFormatParams.format(dtFormat.parse(dataFim));
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("DATA_INICIO", dataIni);
		params.put("DATA_FIM", dateF);

		byte[] pdf = serviceRelatorio.gerarRelatorio("relatorio-usuario-param",params,request.getServletContext());

		String base64Pdf = "data:application/pdf;base64," + org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(pdf);
		
		return new ResponseEntity<String>(base64Pdf,HttpStatus.OK);
			
	}
	
}
