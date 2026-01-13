package com.example.smartcity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        long start = System.currentTimeMillis();
        try {
            String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
            LOG.info("Incoming request {} {}{} from={} traceId={}", request.getMethod(), request.getRequestURI(), query, request.getRemoteAddr(), traceId);
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            LOG.info("Completed request {} {} status={} durationMs={} traceId={}", request.getMethod(), request.getRequestURI(), response.getStatus(), duration, traceId);
            MDC.remove("traceId");
        }
    }
}
