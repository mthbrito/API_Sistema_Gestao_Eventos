package ifpb.app_sistema_gestao_eventos.config;

import ifpb.app_sistema_gestao_eventos.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/sge/usuarios").permitAll()
                        .requestMatchers("/api/sge/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/perfis/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/perfis/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/salas/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/salas/**").hasRole("ADMIN")
                        .requestMatchers("/api/sge/eventos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/inscricoes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/notificacoes/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
