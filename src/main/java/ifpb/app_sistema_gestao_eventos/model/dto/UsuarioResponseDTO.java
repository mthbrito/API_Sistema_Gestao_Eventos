package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoFuncao;
import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;

import java.time.LocalDate;
import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoFuncao funcao,
        LocalDate dataCadastro,
        List<TipoPerfil> perfis
) {
}
