package br.com.apirest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.apirest.model.Profissao;
import br.com.apirest.model.Telefone;
import br.com.apirest.model.Usuario;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String nome;
	private String cpf;
	
	@JsonFormat(pattern="yyyy-mm-dd")
	private Date data_nascimento;

    private Integer profissao;

	private List<Telefone> telefones;

	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.setData_nascimento(usuario.getData_nascimento());
		this.setTelefones(usuario.getTelefones());
		this.setProfissao(usuario.getProfissao());
	
	}
	
	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Integer getProfissao() {
		return profissao;
	}

	public void setProfissao(Integer profissao) {
		this.profissao = profissao;
	}









}
