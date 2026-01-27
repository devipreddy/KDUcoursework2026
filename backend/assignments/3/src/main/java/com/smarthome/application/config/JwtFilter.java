package com.smarthome.application.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import com.smarthome.application.config.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            log.debug("No Authorization header or Bearer token not present");
        } else if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = authHeader.substring(SecurityConstants.BEARER_PREFIX.length());
            if (jwtUtil.isValid(token)) {
                String username = jwtUtil.getUsername(token);
                var authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(SecurityConstants.ROLE_USER)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("JWT valid - authentication set for user {}", username);
            } else {
                log.debug("Invalid JWT token seen in request");
            }
        }

        chain.doFilter(request, response);
    }
}



