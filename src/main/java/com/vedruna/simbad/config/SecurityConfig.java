package com.vedruna.simbad.config;

import com.vedruna.simbad.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF ya que se usa JWT
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/SimbadAPI/v1/auth/**").permitAll() // Permitir acceso sin autenticación
                        .requestMatchers("/public/images/**").permitAll() // Permitir acceso sin autenticación a los recursos en /uploads/**
                        .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No guardar estado de sesión
                .authenticationProvider(authProvider) // Configurar el proveedor de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añadir el filtro JWT antes del filtro de autenticación de usuario y contraseña
                .build();
    }
}
