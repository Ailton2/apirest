package br.com.apirest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.apirest.ApplicationContextLoad;
import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	//tempo da validade do token 2 dias
	private static final long EXPIRATION_TIME = 172800000;
	
	//Uma senha unica para compor a autenticacao
	private static final String SECRET = "SenhaSecreta";
	
	//prefixo padrao de token
	private static final String TOKEN_PREFIX = "Bearer";
	
	
	private static final String HEADER_STRING = "Authorization";
	
	//Gerando token de autencitacao e adicionando ao cabecalho e resposta Http
	public void addAuthentication(HttpServletResponse response, String username) throws IOException{
		
		//montagem do token
		String JWT = Jwts.builder()
				.setSubject(username)//adiciona o usuario
				.setExpiration(new Date(System.currentTimeMillis() +  EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,SECRET).compact();
		
		//junta o token com o prefixo
		String token = TOKEN_PREFIX + " " + JWT;
		
		//adicona no cabecalho http
		response.addHeader(HEADER_STRING, token);
		
		//escreve token como resposta no corpo http
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	//retorna o usuario valido com token ou casa nao seja valido retorna null
	public Authentication getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if(token != null) {
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();
			if(user != null) {
				Usuario usuario = ApplicationContextLoad.getApplicationContext()
						.getBean(RepositoryUsuario.class).findUserByLogin(user);
				if(user != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getPassword(), 
							usuario.getAuthorities());
				}
			}
		}
		return null;
	}

}
