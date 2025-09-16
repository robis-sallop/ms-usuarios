package cl.robis.sallop.ms.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoRequestDTO {
    @Schema(description = "Número de teléfono", example = "123456789")
    private String number;
    @Schema(description = "Código de ciudad", example = "1")
    private String citycode;
    @Schema(description = "Código de país", example = "56")
    private String contrycode;
}
