package ifpb.app_sistema_gestao_eventos.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RedefinirSenhaRequestDTO(
        @NotBlank String token,
        @NotBlank @Size(min = 4) String novaSenha
) {}