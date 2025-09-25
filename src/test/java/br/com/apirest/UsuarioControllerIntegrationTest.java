package br.com.apirest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUsuarios() throws Exception {
        mockMvc.perform(get("/usuario"))
            .andExpect(status().isOk());
    }

    @Test
    void testPostUsuario() throws Exception {
        String usuarioJson = "{\"login\":\"testuser\",\"nome\":\"Test User\"}";
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson))
            .andExpect(status().isCreated());
    }
}
