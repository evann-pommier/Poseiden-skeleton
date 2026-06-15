package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .logout(logout -> logout
                        .logoutUrl("/app/logout")
                        .logoutSuccessUrl("/app/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error")
                )
                .build();
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