package com.example.docmenuservice.config;

import com.example.docmenuservice.config.jwt.JwtAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Configuration
public class SecurityConfig {
    private final JwtAuthEntryPoint authEntryPoint;

    public SecurityConfig( JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("doc-service/v3/api-docs/**", "doc-service/swagger-ui/**", "doc-service/swagger-ui.html").permitAll();
                    request.requestMatchers(HttpMethod.POST,
                            "/api/v1/image",
                            "/api/v1/image/{userId}",
                            "/api/v1/main-titles/sub-title/{id}",
                            "/api/v1/main-titles",
                            "/api/v1/main-titles/uploadMultipleFiles?id={id}",
                            "/api/v1/main-titles/file-upload/{id}",
                            "/api/v1/departments",
                            "/api/v1/contents/{id}",
                            "/api/v1/favorites"
                            ).permitAll();
                    request.requestMatchers(HttpMethod.POST,
                            "/api/v1/image",
                            "/api/v1/auth/temporary-credentials").permitAll();

                    request.requestMatchers(HttpMethod.PUT,
                            "/api/v1/main-titles/{id}",
                            "/api/v1/main-titles/sub-title/{id}",
                            "/api/v1/departments/{id}",
                            "/api/v1/contents/{id}"
                    ).permitAll();

                    request.requestMatchers(HttpMethod.GET,
                            "/api/v1/users/{userId}",
                            "/api/v1/users",
                            "/api/v1/main-titles/{id}",
                            "/api/v1/main-titles",
                            "/api/v1/main-titles/sub-titles/{id}",
                            "/api/v1/main-titles/sub-title/{id}",
                            "/api/v1/main-titles/download/fileName",
                            "/api/v1/departments/{id}",
                            "/api/v1/departments",
                            "/api/v1/contents/{contentId}",
                            "/api/v1/favorites/{userId}"
                            ).permitAll();

                    request.requestMatchers(HttpMethod.DELETE,
                            "/api/v1/main-titles/{id}",
                            "/api/v1/main-titles/sub-title/{subId}",
                            "api/v1/main-titles/fileUploads/{fileId}",
                            "/api/v1/departments/{id}",
                            "/api/v1/favorites/{id}"
                            ).permitAll();
                })
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
        return http.build();
    }


}
