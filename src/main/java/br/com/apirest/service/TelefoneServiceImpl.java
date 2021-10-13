package br.com.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.repository.RepositoryTelefone;

@Service
public class TelefoneServiceImpl implements TelefoneService {
	
	@Autowired
	private RepositoryTelefone repositoryTelefone;

	@Override
	public void deletarTel(Long id) {
		repositoryTelefone.deleteById(id);
	}

}
