package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoFuncao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroRequestDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 4)
        String senha,

        TipoFuncao funcao
) {}
