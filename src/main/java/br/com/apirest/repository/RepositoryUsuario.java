package br.com.apirest.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apirest.model.Usuario;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
	
	@Transactional
	@Modifying
	@Query("update Usuario set token = ?1 where login = ?2")
	void atualizaTokenUser(String token, String login);
	
	@Query("select u from Usuario u where u.nome like %:nome%" )
	List<Usuario> findUserByNome(String nome);
}
