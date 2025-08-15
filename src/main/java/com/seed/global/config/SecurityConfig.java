package com.seed.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // REST API면 보통 끔
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // 있어도 되고 없어도 됨
        return http.build();
    }
}
