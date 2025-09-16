package cl.robis.sallop.ms.usuarios.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {
    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String name;
    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String email;
    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;
    @Schema(description = "Lista de teléfonos del usuario")
    private List<TelefonoRequestDTO> phones;
}
