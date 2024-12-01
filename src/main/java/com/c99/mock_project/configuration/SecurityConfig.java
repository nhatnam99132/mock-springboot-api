package com.c99.mock_project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if necessary
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()  // Only require authentication for /api/**
                        .anyRequest().permitAll())  // Allow all other requests without authentication
                .formLogin(Customizer.withDefaults())  // Enable form login (optional)
                .httpBasic(Customizer.withDefaults());  // Enable HTTP Basic Authentication
        return http.build();
    }
}

