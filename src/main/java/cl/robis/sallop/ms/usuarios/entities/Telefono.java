package cl.robis.sallop.ms.usuarios.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Telefono {
    @Schema(description = "ID único del teléfono", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Número de teléfono", example = "123456789")
    private String number;
    @Schema(description = "Código de ciudad", example = "1")
    private String citycode;
    @Schema(description = "Código de país", example = "56")
    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @Schema(description = "Usuario asociado a este teléfono")
    private Usuario usuario;
}
