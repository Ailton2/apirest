package br.com.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.model.Profissao;

@Repository
public interface RepositoryProfissao extends JpaRepository<Profissao, Long> {

}
