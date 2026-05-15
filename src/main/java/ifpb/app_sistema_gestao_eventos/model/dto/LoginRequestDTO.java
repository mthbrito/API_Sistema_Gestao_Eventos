package ifpb.app_sistema_gestao_eventos.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String senha
) {
}
