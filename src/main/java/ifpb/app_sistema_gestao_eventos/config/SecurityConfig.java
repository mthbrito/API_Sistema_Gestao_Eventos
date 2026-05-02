package ifpb.app_sistema_gestao_eventos.config;

import org.springframework.context.annotation.Bean;

public class SecurityConfig {

    @Bean
    public <HttpSecurity> SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/eventos/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
