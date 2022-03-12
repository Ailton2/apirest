package br.com.apirest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apirest.model.Usuario;

@Repository
public class UsuarioDao {
	
	@Autowired
	private EntityManager entityManager;

	public List<Usuario> buscarListaDeUsuarios() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));
		
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Usuario> list = typedQuery.getResultList();
		return list;

	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
		
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
		Usuario usuario = typedQuery.getSingleResult();
		return usuario;
	}
	
	public List<Usuario> buscarUsuariosPorNome(String nome) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.like(root.get("nome"),'%'+nome+'%'));
		
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Usuario> usuarios = typedQuery.getResultList();
		return usuarios;
	}
}
