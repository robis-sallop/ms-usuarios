package cl.robis.sallop.ms.usuarios.service;

import cl.robis.sallop.ms.usuarios.dto.UsuarioRequestDTO;
import cl.robis.sallop.ms.usuarios.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) throws Exception;
    List<UsuarioResponseDTO> obtenerTodos();
}
