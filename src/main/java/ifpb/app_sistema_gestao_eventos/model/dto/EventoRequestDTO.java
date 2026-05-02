package ifpb.app_sistema_gestao_eventos.model.dto;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoEvento;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record EventoRequestDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String titulo,

        @NotBlank
        @Size(min = 5, max = 300)
        String descricao,

        @NotNull
        @Future
        LocalDate dataInicio,

        @NotNull
        LocalDate dataTermino,

        @NotNull
        TipoEvento tipoEvento,

        @NotNull
        Long organizadorId,

        @NotNull
        Long salaId
) {
}
