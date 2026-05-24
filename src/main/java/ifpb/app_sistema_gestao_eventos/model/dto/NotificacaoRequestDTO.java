package ifpb.app_sistema_gestao_eventos.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacaoRequestDTO(
        @NotBlank
        @Size(min = 3, max = 255)
        String mensagem,

        Long usuarioId
) {
}
