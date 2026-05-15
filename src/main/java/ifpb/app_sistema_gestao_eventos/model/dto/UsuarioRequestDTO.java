package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoFuncao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioRequestDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String senha,

        TipoFuncao funcao,

        @NotEmpty
        List<Long> perfisIds
) {
}
