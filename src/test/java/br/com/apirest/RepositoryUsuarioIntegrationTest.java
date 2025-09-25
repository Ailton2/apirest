package br.com.apirest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.apirest.model.Usuario;
import br.com.apirest.repository.RepositoryUsuario;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryUsuarioIntegrationTest {

    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Test
    void testSaveAndFindUserByLogin() {
        Usuario usuario = new Usuario();
        usuario.setLogin("testlogin");
        usuario.setNome("Test User");
        repositoryUsuario.save(usuario);

        Usuario found = repositoryUsuario.findUserByLogin("testlogin");
        assertNotNull(found);
        assertEquals("Test User", found.getNome());
    }

    @Test
    void testFindUserByNome() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test2");
        usuario.setNome("NomeBusca");
        repositoryUsuario.save(usuario);

        List<Usuario> found = repositoryUsuario.findUserByNome("NomeBusca");
        assertFalse(found.isEmpty());
        assertEquals("NomeBusca", found.get(0).getNome());
    }
}
