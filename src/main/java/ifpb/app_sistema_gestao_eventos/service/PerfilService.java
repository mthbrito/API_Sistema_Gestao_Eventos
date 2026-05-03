package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;
import ifpb.app_sistema_gestao_eventos.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper.toPerfil;
import static ifpb.app_sistema_gestao_eventos.mapper.PerfilMapper.toPerfilResponseDTO;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public List<PerfilResponseDTO> listarPerfis() {
        return perfilRepository.findAll()
                .stream()
                .map(PerfilMapper::toPerfilResponseDTO)
                .toList();
    }

    public PerfilResponseDTO buscarPerfilPorId(Long id) {
        return perfilRepository.findById(id)
                .map(PerfilMapper::toPerfilResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Perfil não encontrado"));
    }

    public PerfilResponseDTO salvarPerfil(PerfilRequestDTO perfil) {
        Perfil novoPerfil = toPerfil(perfil);
        perfilRepository.save(novoPerfil);
        return toPerfilResponseDTO(novoPerfil);
    }

    public PerfilResponseDTO atualizarPerfil(Long id, PerfilRequestDTO perfil) {
        Perfil perfilAtualizado = perfilRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Perfil não encontrado"));
        perfilAtualizado.setNome(perfil.nome());
        return PerfilMapper.toPerfilResponseDTO(perfilRepository.save(perfilAtualizado));
    }

    public void deletarPerfil(Long id) {
        perfilRepository.deleteById(id);
    }
}
