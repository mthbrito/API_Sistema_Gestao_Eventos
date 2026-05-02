package ifpb.app_sistema_gestao_eventos.config;

import ifpb.app_sistema_gestao_eventos.security.JwtFilter;
import ifpb.app_sistema_gestao_eventos.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService, JwtFilter jwtFilter) {
        this.usuarioDetailsService = usuarioDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/sge/auth/login").permitAll()
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
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}