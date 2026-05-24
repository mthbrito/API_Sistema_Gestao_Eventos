package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Notificacao;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;

public class NotificacaoMapper {

    public static Notificacao toNotificacao(NotificacaoRequestDTO notificacao, Usuario usuario) {
        return new Notificacao(
                notificacao.mensagem(),
                usuario
        );
    }

    public static NotificacaoResponseDTO toNotificacaoResponseDTO(Notificacao notificacao) {
        String usuarioNome = null;

        if (notificacao.getUsuario() != null) {
            usuarioNome = notificacao.getUsuario().getNome();
        }

        return new NotificacaoResponseDTO(
                notificacao.getId(),
                notificacao.getMensagem(),
                notificacao.getDataEnvio(),
                notificacao.isLida(),
                usuarioNome
        );
    }

}
