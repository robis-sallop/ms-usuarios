package cl.robis.sallop.ms.usuarios.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Usuario {
    @Schema(description = "ID único del usuario", example = "b3b1c2d3-4e5f-6789-0123-456789abcdef")
    @Id
    private UUID id;
    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String name;
    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    @Column(unique = true)
    private String email;
    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;
    @Schema(description = "Fecha de creación del usuario", example = "2024-09-15T12:34:56")
    private LocalDateTime created;
    @Schema(description = "Fecha de última modificación", example = "2024-09-15T12:34:56")
    private LocalDateTime modified;
    @Schema(description = "Fecha de último login", example = "2024-09-15T12:34:56")
    private LocalDateTime lastLogin;
    @Schema(description = "Token JWT generado para el usuario")
    private String token;
    @Schema(description = "Indica si el usuario está activo", example = "true")
    private boolean isactive;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Schema(description = "Lista de teléfonos asociados al usuario")
    private List<Telefono> phones;
}
