package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeJaCadastradaException;
import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.SalaMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Sala;
import ifpb.app_sistema_gestao_eventos.repository.SalaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static ifpb.app_sistema_gestao_eventos.mapper.SalaMapper.toSala;
import static ifpb.app_sistema_gestao_eventos.mapper.SalaMapper.toSalaResponseDTO;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Page<SalaResponseDTO> listarSalas(Pageable pageable) {
        return salaRepository.findAll(pageable)
                .map(SalaMapper::toSalaResponseDTO);
    }

    public SalaResponseDTO buscarSalaPorId(Long id) {
        return salaRepository.findById(id)
                .map(SalaMapper::toSalaResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada"));
    }

    public SalaResponseDTO salvarSala(SalaRequestDTO sala) {
        if (salaRepository.existsByNomeAndLocalizacao(sala.nome(), sala.localizacao())) {
            throw new EntidadeJaCadastradaException("Já existe uma sala com esse nome nessa localização");
        }
        Sala novaSala = toSala(sala);
        return toSalaResponseDTO(salaRepository.save(novaSala));
    }

    public SalaResponseDTO atualizarSala(Long id, SalaRequestDTO sala) {
        Sala salaAtualizada = salaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada"));
        if (salaRepository.existsByNomeAndLocalizacaoAndIdNot(sala.nome(), sala.localizacao(), id)) {
            throw new EntidadeJaCadastradaException("Já existe uma sala com esse nome nessa localização");
        }
        salaAtualizada.setNome(sala.nome());
        salaAtualizada.setCapacidade(sala.capacidade());
        salaAtualizada.setLocalizacao(sala.localizacao());
        return toSalaResponseDTO(salaRepository.save(salaAtualizada));
    }

    public void deletarSala(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Sala não encontrada");
        }
        salaRepository.deleteById(id);
    }

}
