package com.nnk.springboot.security;

import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Configuration Spring Security de l'application.
 *
 * <p>Définit les règles d'accès, le formulaire de login, la gestion de session
 * (une session active par utilisateur) et l'encodage BCrypt des mots de passe.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures session-based authentication and protected routes.
     *
     * @param http the HTTP security builder
     * @return the configured security filter chain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/login", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .loginProcessingUrl("/app/login")
                        .defaultSuccessUrl("/bidList/list", true)
                        .failureUrl("/app/login?error=true")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                        .invalidSessionUrl("/app/login?invalidSession=true")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/app/login?expired=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/app/logout")
                        .logoutSuccessUrl("/app/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/app/login?expired=true"))
                )
                .build();
    }

    /**
     * Publishes HTTP session lifecycle events so Spring Security can track
     * active and expired sessions correctly.
     *
     * @return the HTTP session event publisher
     */
    @Bean
    public HttpSessionListener httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Provides BCrypt password encoding for user passwords.
     *
     * @return a BCrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}