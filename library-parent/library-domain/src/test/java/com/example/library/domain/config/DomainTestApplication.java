package com.example.library.domain.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Test-only Spring Boot configuration for the domain module.
 *
 * <p>This configuration explicitly enables JPA entity and repository scanning
 * for repository slice tests.</p>
 *
 * <p>Exists ONLY in test scope.</p>
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.example.library.domain.entity")
@EnableJpaRepositories(basePackages = "com.example.library.domain.repository")
public class DomainTestApplication {
}
