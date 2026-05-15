package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeJaCadastradaException;
import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;
import ifpb.app_sistema_gestao_eventos.repository.PerfilRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper.toPerfil;
import static ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper.toPerfilResponseDTO;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Page<PerfilResponseDTO> listarPerfis(Pageable pageable) {
        return perfilRepository.findAll(pageable)
                .map(PerfilMapper::toPerfilResponseDTO);
    }

    public PerfilResponseDTO buscarPerfilPorId(Long id) {
        return perfilRepository.findById(id)
                .map(PerfilMapper::toPerfilResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Perfil não encontrado"));
    }

    public PerfilResponseDTO salvarPerfil(PerfilRequestDTO perfil) {
        if (perfilRepository.existsByNome(perfil.nome())) {
            throw new EntidadeJaCadastradaException("Perfil já cadastrado");
        }
        return toPerfilResponseDTO(perfilRepository.save(toPerfil(perfil)));
    }

    public PerfilResponseDTO atualizarPerfil(Long id, PerfilRequestDTO perfil) {
        Perfil perfilAtualizado = perfilRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Perfil não encontrado"));
        if (perfilRepository.existsByNomeAndIdNot(perfil.nome(), id)) {
            throw new EntidadeJaCadastradaException("Perfil já cadastrado");
        }
        perfilAtualizado.setNome(perfil.nome());
        return toPerfilResponseDTO(perfilRepository.save(perfilAtualizado));
    }

    public void deletarPerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Perfil não encontrado");
        }
        perfilRepository.deleteById(id);
    }
}
