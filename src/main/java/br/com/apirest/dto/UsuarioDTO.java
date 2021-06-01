package br.com.apirest.dto;

import java.io.Serializable;

import br.com.apirest.model.Usuario;

public class UsuarioDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
    private String login;
	private String nome;
	private String cpf;
	
	
	public UsuarioDTO(Usuario usuario) {
		this.login = usuario.getLogin();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		
	}
	
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
}
