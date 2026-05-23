package ifpb.app_sistema_gestao_eventos.config;

import ifpb.app_sistema_gestao_eventos.model.entity.Perfil;
import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoFuncao;
import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;
import ifpb.app_sistema_gestao_eventos.repository.PerfilRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PerfilRepository perfilRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarPerfis();
        criarAdminPadrao();
    }

    private void criarPerfis() {
        for (TipoPerfil tipo : TipoPerfil.values()) {
            if (!perfilRepository.existsByNome(tipo)) {
                perfilRepository.save(new Perfil(tipo));
                System.out.println("[DataInitializer] Perfil criado: " + tipo);
            }
        }
    }

    private void criarAdminPadrao() {
        String emailAdmin = "admin@admin.com";

        if (usuarioRepository.existsByEmail(emailAdmin)) {
            return;
        }

        Perfil perfilAdmin = perfilRepository.findByNome(TipoPerfil.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Perfil ADMIN não encontrado após seed"));

        Usuario admin = new Usuario(
                "Administrador",
                emailAdmin,
                passwordEncoder.encode("admin123"),
                TipoFuncao.SERVIDOR
        );
        admin.setPerfis(List.of(perfilAdmin));

        usuarioRepository.save(admin);
        System.out.println("[DataInitializer] Usuário admin criado — email: " + emailAdmin + " | senha: admin123");
    }
}
