package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Inscricao;

public class InscricaoMapper {

    public static InscricaoResponseDTO toInscricaoResponseDTO(Inscricao inscricao) {
        return new InscricaoResponseDTO(
                inscricao.getId(),
                inscricao.getDataInscricao(),
                inscricao.getStatus(),
                inscricao.isPresente(),
                inscricao.getUsuario().getId(),
                inscricao.getUsuario().getNome(),
                inscricao.getEvento().getId(),
                inscricao.getEvento().getTitulo()
        );
    }
}
