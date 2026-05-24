package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoEvento;

import java.time.LocalDate;

public record EventoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataTermino,
        TipoEvento tipoEvento,
        Long organizadorId,
        String organizadorNome,
        Long salaId,
        String salaNome,
        String salaLocalizacao,
        int totalInscricoes
) {}
