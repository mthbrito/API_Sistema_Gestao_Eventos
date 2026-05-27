package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;

import java.util.List;

public record LoginResponseDTO(
        String token,
        Long id,
        String nome,
        String email,
        List<TipoPerfil> perfis
) {
}