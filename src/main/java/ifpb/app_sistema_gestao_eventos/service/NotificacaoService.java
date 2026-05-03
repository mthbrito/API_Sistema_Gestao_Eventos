package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.mapper.NotificacaoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Notificacao;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.repository.NotificacaoRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
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

    public List<NotificacaoResponseDTO> listarNotificacoes() {
        return notificacaoRepository.findAll()
                .stream()
                .map(NotificacaoMapper::toNotificacaoResponseDTO)
                .toList();
    }

    public NotificacaoResponseDTO buscarNotificacaoPorId(Long id) {
        return notificacaoRepository.findById(id)
                .map(NotificacaoMapper::toNotificacaoResponseDTO)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
    }

    public NotificacaoResponseDTO salvarNotificacao(NotificacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Notificacao novaNotificacao = NotificacaoMapper.toNotificacao(dto, usuario);
        return NotificacaoMapper.toNotificacaoResponseDTO(notificacaoRepository.save(novaNotificacao));
    }

    public NotificacaoResponseDTO atualizarNotificacao(Long id, NotificacaoRequestDTO notificacao) {
        Notificacao notificacaoAtualizada = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacaoAtualizada.setMensagem(notificacao.mensagem());
        notificacaoAtualizada.setLida(notificacao.lida());
        return NotificacaoMapper.toNotificacaoResponseDTO(notificacaoRepository.save(notificacaoAtualizada));
    }

    public void deletarNotificacao(Long id) {
        notificacaoRepository.deleteById(id);
    }
}
