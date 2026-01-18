package com.example.library.service.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.example.library.service"
})
@EntityScan(basePackages = "com.example.library.domain.entity")
@EnableJpaRepositories(basePackages = "com.example.library.domain.repository")
@Import(TestSecurityConfig.class)
public class ServiceTestApplication {
}
