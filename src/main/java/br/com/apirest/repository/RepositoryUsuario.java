package br.com.apirest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.model.Usuario;

@Repository
public interface RepositoryUsuario extends CrudRepository<Usuario, Long> {

}
