package cl.robis.sallop.ms.usuarios.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    @Schema(description = "ID único del usuario", example = "b3b1c2d3-4e5f-6789-0123-456789abcdef")
    private UUID id;
    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String name;
    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String email;
    @Schema(description = "Fecha de creación del usuario", example = "2024-09-15T12:34:56")
    private LocalDateTime created;
    @Schema(description = "Fecha de última modificación", example = "2024-09-15T12:34:56")
    private LocalDateTime modified;
    @Schema(description = "Fecha de último login", example = "2024-09-15T12:34:56")
    private LocalDateTime lastLogin;
    @Schema(description = "Token JWT generado para el usuario")
    private String token;
    @Schema(description = "Indica si el usuario está activo", example = "true")
    @JsonProperty("isactive")
    private boolean isactive;
    @Schema(description = "Lista de teléfonos del usuario")
    private List<TelefonoRequestDTO> phones;
}
