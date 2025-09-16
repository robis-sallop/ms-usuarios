package cl.robis.sallop.ms.usuarios.service;

import cl.robis.sallop.ms.usuarios.dto.TelefonoRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.UsuarioRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.UsuarioResponseDTO;
import cl.robis.sallop.ms.usuarios.entities.Usuario;
import cl.robis.sallop.ms.usuarios.repository.UsuarioRepository;
import cl.robis.sallop.ms.usuarios.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_exitoso() throws Exception {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .name("Juan Perez")
                .email("juan@mail.com")
                .password("Abcde12")
                .phones(Collections.singletonList(
                        TelefonoRequestDTO.builder().number("123456789").citycode("1").contrycode("56").build()
                ))
                .build();
        when(usuarioRepository.findByEmail("juan@mail.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDTO response = usuarioService.crearUsuario(request);
        assertEquals("Juan Perez", response.getName());
        assertEquals("juan@mail.com", response.getEmail());
        assertNotNull(response.getToken());
        assertTrue(response.isIsactive());
        assertEquals(1, response.getPhones().size());
    }

    @Test
    void crearUsuario_emailInvalido() {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .name("Juan Perez")
                .email("juanmail.com")
                .password("Abcde12")
                .build();
        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(request));
        assertTrue(ex.getMessage().contains("correo"));
    }

    @Test
    void crearUsuario_passwordInvalida() {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .name("Juan Perez")
                .email("juan@mail.com")
                .password("abc")
                .build();
        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(request));
        assertTrue(ex.getMessage().contains("clave"));
    }

    @Test
    void crearUsuario_emailDuplicado() {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .name("Juan Perez")
                .email("juan@mail.com")
                .password("Abcde12")
                .build();
        when(usuarioRepository.findByEmail("juan@mail.com")).thenReturn(Optional.of(new Usuario()));
        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(request));
        assertTrue(ex.getMessage().contains("registrado"));
    }

    @Test
    void obtenerTodos_retornaLista() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setName("Juan Perez");
        usuario.setEmail("juan@mail.com");
        usuario.setPassword("Abcde12");
        usuario.setCreated(java.time.LocalDateTime.now());
        usuario.setModified(java.time.LocalDateTime.now());
        usuario.setLastLogin(java.time.LocalDateTime.now());
        usuario.setIsactive(true);
        usuario.setToken("token");
        usuario.setPhones(new ArrayList<>());
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        List<UsuarioResponseDTO> lista = usuarioService.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Juan Perez", lista.get(0).getName());
    }
}

