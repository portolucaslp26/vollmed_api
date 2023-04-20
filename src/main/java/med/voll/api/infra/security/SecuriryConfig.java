package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
// @EnableWebSecurity serve para "informar" que vamos personalizar as configurações de segurança
@EnableWebSecurity
public class SecuriryConfig {

    @Autowired
    private SecurityFilter securityFilter;


    @Bean
    // @Bean serve para "expor" o retorno do método
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // desabilita proteção contra ataques csrf
        // configurando projeto para ser Stateless
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // autorizando requests
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/pacientes").permitAll()
                .requestMatchers(HttpMethod.POST, "/medicos").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/hello", "/auth" ).permitAll()
                // barrando todas as outras requests
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Injeta o AuthenticationManager
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
