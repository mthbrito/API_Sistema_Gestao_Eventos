package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;

public record PerfilResponseDTO(
        Long id,
        TipoPerfil nome
) {
}
