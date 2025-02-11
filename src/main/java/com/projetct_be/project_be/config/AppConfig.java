package com.projetct_be.project_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Anda bisa mengganti "*" dengan domain tertentu saat diperlukan
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("X-Requested-With", "Content-Type", "Origin", "Authorization", "Accept", "Client-Security-Token", "Accept-Encoding")
                .maxAge(3600);  // Maksimal waktu dalam detik untuk caching preflight request
    }
}
