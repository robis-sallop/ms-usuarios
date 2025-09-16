package cl.robis.sallop.ms.usuarios.controller;

import cl.robis.sallop.ms.usuarios.dto.UsuarioRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.TelefonoRequestDTO;
import cl.robis.sallop.ms.usuarios.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioRequestDTO usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        TelefonoRequestDTO tel = new TelefonoRequestDTO();
        tel.setNumber("1234567");
        tel.setCitycode("1");
        tel.setContrycode("57");
        usuarioValido = new UsuarioRequestDTO();
        usuarioValido.setName("Juan Rodriguez");
        usuarioValido.setEmail("juan@rodriguez.org");
        usuarioValido.setPassword("Hunter22");
        usuarioValido.setPhones(Collections.singletonList(tel));
    }

    @Test
    void crearUsuarioExitoso() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.modified").exists())
                .andExpect(jsonPath("$.lastLogin").exists())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.isactive").value(true));
    }

    @Test
    void crearUsuarioCorreoDuplicado() throws Exception {
        // Crear usuario primero
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isCreated());
        // Intentar crear con el mismo correo
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("El correo ya registrado"));
    }

    @Test
    void crearUsuarioCorreoInvalido() throws Exception {
        usuarioValido.setEmail("correo_invalido");
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("El formato de correo es incorrecto"));
    }

    @Test
    void crearUsuarioPasswordInvalida() throws Exception {
        usuarioValido.setPassword("abcde");
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("La clave no cumple con el formato requerido"));
    }

    @Test
    void obtenerTodosUsuariosExitoso() throws Exception {
        // Crear un usuario para que la lista no esté vacía
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("juan@rodriguez.org"));
    }
}
