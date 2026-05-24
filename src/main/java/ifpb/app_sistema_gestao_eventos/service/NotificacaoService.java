package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.NotificacaoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Notificacao;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.repository.NotificacaoRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository, UsuarioRepository usuarioRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;

    }

    public Page<NotificacaoResponseDTO> listarNotificacoes(Pageable pageable) {
        return notificacaoRepository.findAll(pageable)
                .map(NotificacaoMapper::toNotificacaoResponseDTO);
    }

    public NotificacaoResponseDTO buscarNotificacaoPorId(Long id) {
        return notificacaoRepository.findById(id)
                .map(NotificacaoMapper::toNotificacaoResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Notificação não encontrada"));
    }

    public List<NotificacaoResponseDTO> salvarNotificacao(NotificacaoRequestDTO dto) {
        if (dto.usuarioId() == null) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(usuario -> {
                        Notificacao n = NotificacaoMapper.toNotificacao(dto, usuario);
                        return NotificacaoMapper.toNotificacaoResponseDTO(notificacaoRepository.save(n));
                    })
                    .toList();
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        Notificacao n = NotificacaoMapper.toNotificacao(dto, usuario);
        return List.of(NotificacaoMapper.toNotificacaoResponseDTO(notificacaoRepository.save(n)));
    }

    public NotificacaoResponseDTO atualizarNotificacao(Long id, NotificacaoRequestDTO dto) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Notificação não encontrada"));
        notificacao.setMensagem(dto.mensagem());
        return NotificacaoMapper.toNotificacaoResponseDTO(notificacaoRepository.save(notificacao));
    }

    public void deletarNotificacao(Long id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Notificação não encontrada");
        }
        notificacaoRepository.deleteById(id);
    }
}
