package cl.robis.sallop.ms.usuarios.repository;

import cl.robis.sallop.ms.usuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
}

