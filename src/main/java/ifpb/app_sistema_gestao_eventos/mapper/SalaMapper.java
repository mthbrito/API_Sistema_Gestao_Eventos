package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.SalaRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Sala;

public class SalaMapper {

    public static Sala toSala(SalaRequestDTO sala) {
        return new Sala(
                sala.nome(),
                sala.capacidade(),
                sala.localizacao()
        );
    }

    public static SalaResponseDTO toSalaResponseDTO(Sala sala) {
        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getLocalizacao()
        );
    }
}
