package ifpb.app_sistema_gestao_eventos.config;

import ifpb.app_sistema_gestao_eventos.security.JwtFilter;
import ifpb.app_sistema_gestao_eventos.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
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
                        .requestMatchers(HttpMethod.POST, "/api/sge/auth/recuperar-senha").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sge/auth/redefinir-senha").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sge/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sge/auth/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sge/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/usuarios/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/sge/usuarios/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/perfis/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/perfis/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/salas/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/salas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/eventos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/eventos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/inscricoes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/sge/inscricoes").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/sge/inscricoes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/inscricoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sge/notificacoes/usuario/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/sge/notificacoes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/sge/notificacoes/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(this::configureExceptionHandling)
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

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> ex) {
        ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "Token inválido, expirado ou ausente"
                    );
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "Você não tem permissão para acessar este recurso"
                    );
                });
    }
}