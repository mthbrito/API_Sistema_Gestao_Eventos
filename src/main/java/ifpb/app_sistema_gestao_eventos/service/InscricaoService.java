package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeJaCadastradaException;
import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.exception.RegraDeNegocioException;
import ifpb.app_sistema_gestao_eventos.mapper.InscricaoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Evento;
import ifpb.app_sistema_gestao_eventos.model.entity.Inscricao;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.repository.EventoRepository;
import ifpb.app_sistema_gestao_eventos.repository.InscricaoRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<InscricaoResponseDTO> listarInscricoes(Pageable pageable) {
        return inscricaoRepository.findAll(pageable)
                .map(InscricaoMapper::toInscricaoResponseDTO);
    }

    public Page<InscricaoResponseDTO> listarInscricoesPorUsuarioId(Long usuarioId, Pageable pageable) {
        return inscricaoRepository.findAllByUsuarioId(usuarioId, pageable)
                .map(InscricaoMapper::toInscricaoResponseDTO);
    }

    public InscricaoResponseDTO buscarInscricaoPorId(Long id) {
        return inscricaoRepository.findById(id)
                .map(InscricaoMapper::toInscricaoResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada"));
    }

    public InscricaoResponseDTO salvarInscricao(InscricaoRequestDTO inscricao) {
        if (inscricaoRepository.existsByUsuarioIdAndEventoId(inscricao.usuarioId(), inscricao.eventoId())) {
            throw new EntidadeJaCadastradaException("Usuário já inscrito neste evento");
        }
        Usuario usuario = usuarioRepository.findById(inscricao.usuarioId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        Evento evento = eventoRepository.findById(inscricao.eventoId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado"));
        if (inscricaoRepository.countByEventoId(evento.getId()) >= evento.getSala().getCapacidade()) {
            throw new RegraDeNegocioException("Evento sem vagas disponíveis");
        }
        Inscricao novaInscricao = new Inscricao(usuario, evento);
        return toInscricaoResponseDTO(inscricaoRepository.save(novaInscricao));
    }

    public InscricaoResponseDTO atualizarInscricao(Long id, InscricaoRequestDTO inscricao) {
        Inscricao inscricaoAtualizada = inscricaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada"));
        Usuario usuario = usuarioRepository.findById(inscricao.usuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        Evento evento = eventoRepository.findById(inscricao.eventoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado"));
        inscricaoAtualizada.setUsuario(usuario);
        inscricaoAtualizada.setEvento(evento);
        return InscricaoMapper.toInscricaoResponseDTO(inscricaoRepository.save(inscricaoAtualizada));
    }

    public void deletarInscricao(Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Inscrição não encontrada");
        }
        inscricaoRepository.deleteById(id);
    }
}
