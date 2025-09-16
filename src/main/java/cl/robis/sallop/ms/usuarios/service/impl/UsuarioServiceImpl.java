package cl.robis.sallop.ms.usuarios.service.impl;

import cl.robis.sallop.ms.usuarios.dto.TelefonoRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.UsuarioRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.UsuarioResponseDTO;
import cl.robis.sallop.ms.usuarios.entities.Telefono;
import cl.robis.sallop.ms.usuarios.entities.Usuario;
import cl.robis.sallop.ms.usuarios.repository.UsuarioRepository;
import cl.robis.sallop.ms.usuarios.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;

    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=(?:.*\\d.*\\d))[A-Za-z\\d]{6,}$");
    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) throws Exception {
        if (!emailPattern.matcher(request.getEmail()).matches()) {
            System.out.println("[ERROR] Email inválido: " + request.getEmail());
            throw new Exception("El formato de correo es incorrecto");
        }
        if (!passwordPattern.matcher(request.getPassword()).matches()) {
            System.out.println("[ERROR] Password inválida: " + request.getPassword());
            throw new Exception("La clave no cumple con el formato requerido");
        }
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("[ERROR] Correo ya registrado: " + request.getEmail());
            throw new Exception("El correo ya registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setCreated(LocalDateTime.now());
        usuario.setModified(LocalDateTime.now());
        usuario.setLastLogin(LocalDateTime.now());
        usuario.setIsactive(true);
        usuario.setToken(generarToken(request.getEmail()));
        List<Telefono> telefonos = mapPhones(request.getPhones(), usuario);
        usuario.setPhones(telefonos);
        usuarioRepository.save(usuario);
        return mapToResponseDTO(usuario);
    }

    private String generarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(jwtSecretKey)
                .compact();
    }

    private List<Telefono> mapPhones(List<TelefonoRequestDTO> dtos, Usuario usuario) {
        List<Telefono> phones = new ArrayList<>();
        if (dtos != null) {
            for (TelefonoRequestDTO dto : dtos) {
                Telefono tel = new Telefono();
                tel.setNumber(dto.getNumber());
                tel.setCitycode(dto.getCitycode());
                tel.setContrycode(dto.getContrycode());
                tel.setUsuario(usuario);
                phones.add(tel);
            }
        }
        return phones;
    }

    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setEmail(usuario.getEmail());
        dto.setCreated(usuario.getCreated());
        dto.setModified(usuario.getModified());
        dto.setLastLogin(usuario.getLastLogin());
        dto.setToken(usuario.getToken());
        dto.setIsactive(usuario.isIsactive());
        List<TelefonoRequestDTO> phones = new ArrayList<>();
        if (usuario.getPhones() != null) {
            for (Telefono tel : usuario.getPhones()) {
                TelefonoRequestDTO telDto = new TelefonoRequestDTO();
                telDto.setNumber(tel.getNumber());
                telDto.setCitycode(tel.getCitycode());
                telDto.setContrycode(tel.getContrycode());
                phones.add(telDto);
            }
        }
        dto.setPhones(phones);
        return dto;
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        List<UsuarioResponseDTO> lista = new ArrayList<>();
        for (Usuario usuario : usuarioRepository.findAll()) {
            lista.add(mapToResponseDTO(usuario));
        }
        return lista;
    }
}
