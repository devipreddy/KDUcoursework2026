package com.example.library.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.library.domain.repository")
@EntityScan(basePackages = "com.example.library.domain.entity")
public class JpaConfig {
}
