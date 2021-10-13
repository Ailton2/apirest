package br.com.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.model.Telefone;

@Repository
public interface RepositoryTelefone extends JpaRepository<Telefone, Long> {

}
