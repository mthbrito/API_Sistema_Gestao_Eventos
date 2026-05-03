package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.mapper.SalaMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Sala;
import ifpb.app_sistema_gestao_eventos.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.app_sistema_gestao_eventos.mapper.SalaMapper.toSala;
import static ifpb.app_sistema_gestao_eventos.mapper.SalaMapper.toSalaResponseDTO;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<SalaResponseDTO> listarSalas() {
        return salaRepository.findAll()
                .stream()
                .map(SalaMapper::toSalaResponseDTO)
                .toList();
    }

    public SalaResponseDTO buscarSalaPorId(Long id) {
        return salaRepository.findById(id)
                .map(SalaMapper::toSalaResponseDTO)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    public SalaResponseDTO salvarSala(SalaRequestDTO sala) {
        Sala novaSala = toSala(sala);
        salaRepository.save(novaSala);
        return toSalaResponseDTO(novaSala);
    }

    public SalaResponseDTO atualizarSala(Long id, SalaRequestDTO sala) {
        Sala salaAtualizada = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        salaAtualizada.setNome(sala.nome());
        salaAtualizada.setCapacidade(sala.capacidade());
        salaAtualizada.setLocalizacao(sala.localizacao());
        return SalaMapper.toSalaResponseDTO(salaRepository.save(salaAtualizada));
    }

    public void deletarSala(Long id) {
        salaRepository.deleteById(id);
    }

}
