package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.EventoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Evento;

public class EventoMapper {

    public static Evento toEvento(EventoRequestDTO evento) {
        return new Evento(
                evento.titulo(),
                evento.descricao(),
                evento.dataInicio(),
                evento.dataTermino(),
                evento.tipoEvento(),
                null,
                null
        );
    }

    public static EventoResponseDTO toEventoResponseDTO(Evento evento) {
        return new EventoResponseDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getDataInicio(),
                evento.getDataTermino(),
                evento.getTipoEvento(),
                evento.getOrganizador().getId(),
                evento.getOrganizador().getNome(),
                evento.getSala().getId(),
                evento.getSala().getNome(),
                evento.getSala().getLocalizacao(),
                evento.getInscricoes().size()
        );
    }
}
