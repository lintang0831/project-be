package com.projetct_be.project_be.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    // Konfigurasi Swagger untuk API yang ada di package controller Anda
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.projetct_be.project_be.controller"))  // Sesuaikan dengan package controller Anda
                .paths(PathSelectors.any())  // Dokumentasi untuk semua path
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()))  // Menambahkan API key untuk autentikasi JWT
                .securityContexts(Arrays.asList(securityContext()));  // Konfigurasi keamanan
    }

    // Konfigurasi untuk menambahkan resource handler untuk swagger UI
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger-ui/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")  // Menambahkan swagger UI
                        .resourceChain(false);
            }
        };
    }

    // Informasi dasar API
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Project BE API documentation",  // Nama API
                "API documentation for the Project BE",  // Deskripsi API
                "1.0.0",  // Versi API
                "Terms of service URL",
                "Support Contact",
                "License",
                "License URL"
        );
    }

    // Konfigurasi API Key untuk autentikasi JWT di header
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");  // Nama token dan header di mana token akan disertakan
    }

    // Konfigurasi keamanan Swagger dengan JWT
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    // Referensi keamanan untuk JWT
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");  // Ruang lingkup akses
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));  // Menggunakan JWT untuk keamanan
    }
}
