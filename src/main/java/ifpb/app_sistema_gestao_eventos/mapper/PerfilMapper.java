package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.PerfilRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;

public class PerfilMapper {

    public static Perfil toPerfil(PerfilRequestDTO perfil) {
        return new Perfil(perfil.nome());
    }

    public static PerfilResponseDTO toPerfilResponseDTO(Perfil perfil) {
        return new PerfilResponseDTO(
                perfil.getId(),
                perfil.getNome()
        );
    }
}
