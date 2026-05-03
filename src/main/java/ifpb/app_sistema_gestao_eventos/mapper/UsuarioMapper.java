package ifpb.app_sistema_gestao_eventos.mapper;

import ifpb.app_sistema_gestao_eventos.model.dto.UsuarioRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.UsuarioResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioRequestDTO usuario) {
        return new Usuario(
                usuario.nome(),
                usuario.email(),
                usuario.senha(),
                usuario.funcao()
        );
    }

    public static UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getFuncao(),
                usuario.getDataCadastro(),
                usuario.getPerfis()
                        .stream()
                        .map(Perfil::getNome)
                        .toList()
        );
    }
}
