package com.example.library.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.example.library.web.filter.CorrelationIdFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;


import java.time.Instant;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CorrelationIdFilter correlationIdFilter;

    public SecurityConfig(CorrelationIdFilter correlationIdFilter) {
        this.correlationIdFilter = correlationIdFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ✅ ADD FILTER PROPERLY
            .addFilterBefore(
                correlationIdFilter,
                UsernamePasswordAuthenticationFilter.class
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/books/**").authenticated()
                .requestMatchers("/loans/**").authenticated()
                .requestMatchers("/analytics/**").authenticated()
                .anyRequest().permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler())
            )
            .httpBasic();
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

            String correlationId = request.getHeader("X-Correlation-Id");

            response.getWriter().write("""
            {
            "timestamp": "%s",
            "path": "%s",
            "errorCode": "FORBIDDEN",
            "message": "Access is denied",
            "details": null,
            "correlationId": "%s"
            }
            """.formatted(
                Instant.now(),
                request.getRequestURI(),
                correlationId
            ));
        };
    }

}
