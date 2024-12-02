package com.viniciusmassari.gestao_cursos_frontend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/instructor/login").permitAll();
            auth.requestMatchers("/instructor/create").permitAll();
            auth.anyRequest().authenticated();
        }).formLogin(form -> {
            form.loginPage("/instructor/login");
            form.loginProcessingUrl("/perform_login");
        });

        return http.build();
    }
}