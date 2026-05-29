package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeJaCadastradaException;
import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.mapper.UsuarioMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.CadastroRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.UsuarioRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.UsuarioResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;
import ifpb.app_sistema_gestao_eventos.repository.PerfilRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.app_sistema_gestao_eventos.mapper.UsuarioMapper.toUsuario;
import static ifpb.app_sistema_gestao_eventos.mapper.UsuarioMapper.toUsuarioResponseDTO;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(UsuarioMapper::toUsuarioResponseDTO);
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toUsuarioResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }

    public UsuarioResponseDTO buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioMapper::toUsuarioResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("usuário não encontrado"));
    }

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO usuario) {
        if (usuarioRepository.existsByEmail(usuario.email())) {
            throw new EntidadeJaCadastradaException("E-mail já cadastrado");
        }
        Usuario novoUsuario = toUsuario(usuario);
        novoUsuario.setSenha(passwordEncoder.encode(usuario.senha()));
        List<Perfil> perfis = perfilRepository.findAllById(usuario.perfisIds());
        if (perfis.size() != usuario.perfisIds().size()) {
            throw new EntidadeNaoEncontradaException("Um ou mais perfis não encontrados");
        }
        novoUsuario.setPerfis(perfis);
        return toUsuarioResponseDTO(usuarioRepository.save(novoUsuario));
    }

    public UsuarioResponseDTO cadastrarUsuario(CadastroRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EntidadeJaCadastradaException("E-mail já cadastrado");
        }
        Perfil tipoPerfil = perfilRepository.findByNome(TipoPerfil.USER)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Perfil padrão não encontrado"));
        Usuario usuario = new Usuario(
                dto.nome(),
                dto.email(),
                passwordEncoder.encode(dto.senha()),
                dto.funcao()
        );
        usuario.setPerfis(List.of(tipoPerfil));
        return toUsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuario) {
        Usuario usuarioAtualizado = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        if (usuarioRepository.existsByEmailAndIdNot(usuario.email(), id)) {
            throw new EntidadeJaCadastradaException("E-mail já está em uso por outro usuário");
        }
        usuarioAtualizado.setNome(usuario.nome());
        usuarioAtualizado.setEmail(usuario.email());
        if (usuario.senha() != null && !usuario.senha().isBlank()) {
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuario.senha()));
        }
        List<Perfil> perfis = perfilRepository.findAllById(usuario.perfisIds());
        if (perfis.size() != usuario.perfisIds().size()) {
            throw new EntidadeNaoEncontradaException("Um ou mais perfis não encontrados");
        }
        usuarioAtualizado.setPerfis(perfis);
        return UsuarioMapper.toUsuarioResponseDTO(usuarioRepository.save(usuarioAtualizado));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }


}
