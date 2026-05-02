package ifpb.app_sistema_gestao_eventos.model;

import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

public class UsuarioDetails implements UserDetails {

    private Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getFuncao().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
