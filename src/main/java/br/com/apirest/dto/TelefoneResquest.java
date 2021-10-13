package br.com.apirest.dto;

public class TelefoneResquest {
	
	private Long numero;
	private Long usuario_id;
	
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

}
