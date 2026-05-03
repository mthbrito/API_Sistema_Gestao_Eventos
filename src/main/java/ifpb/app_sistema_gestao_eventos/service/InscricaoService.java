package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.InscricaoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Evento;
import ifpb.app_sistema_gestao_eventos.model.entity.Inscricao;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.repository.EventoRepository;
import ifpb.app_sistema_gestao_eventos.repository.InscricaoRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.app_sistema_gestao_eventos.mapper.InscricaoMapper.toInscricaoResponseDTO;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<InscricaoResponseDTO> listarInscricoes() {
        return inscricaoRepository.findAll()
                .stream()
                .map(InscricaoMapper::toInscricaoResponseDTO)
                .toList();
    }

    public InscricaoResponseDTO buscarInscricaoPorId(Long id) {
        return inscricaoRepository.findById(id)
                .map(InscricaoMapper::toInscricaoResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada"));
    }

    public InscricaoResponseDTO salvarInscricao(InscricaoRequestDTO inscricao) {
        Usuario usuario = usuarioRepository.findById(inscricao.usuarioId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        Evento evento = eventoRepository.findById(inscricao.eventoId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado"));
        Inscricao novaInscricao = new Inscricao(usuario, evento);
        inscricaoRepository.save(novaInscricao);
        return toInscricaoResponseDTO(novaInscricao);
    }

    public InscricaoResponseDTO atualizarInscricao(Long id, InscricaoRequestDTO inscricao) {
        Inscricao inscricaoAtualizada = inscricaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada"));
        return InscricaoMapper.toInscricaoResponseDTO(inscricaoRepository.save(inscricaoAtualizada));
    }

    public void deletarInscricao(Long id) {
        inscricaoRepository.deleteById(id);
    }
}
