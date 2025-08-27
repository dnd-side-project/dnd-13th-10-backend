package com.seed.global.config;

import com.seed.global.jwt.JwtTokenFilter;
import com.seed.global.oauth2.handler.OAuth2AuthSuccessHandler;
import com.seed.global.oauth2.service.KakaoOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final OAuth2AuthSuccessHandler oAuth2AuthSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // === CSRF 보안 설정 ===
                // REST API에서는 CSRF 공격 위험이 적으므로 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // H2 콘솔 iframe 허용
                )

                // === CORS 설정 ===
                // 프론트엔드에서 API 호출 시 필요
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // === 세션 관리 설정 ===
                // JWT 토큰을 사용하므로 세션을 생성하지 않음
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // === 요청 권한 설정 ===
                .authorizeHttpRequests(authz -> authz
                        // 인증 없이 접근 가능한 경로들
                        .requestMatchers(
                                "/",                    // 메인 페이지
                                "/login/**",            // 로그인 관련
                                "/oauth2/**",          // OAuth2 관련
                                "/h2-console/**",      // H2 데이터베이스 콘솔
                                "/api/auth/**",   // 인증 관련 API
                                "/static/**",
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**"
                        ).permitAll()

                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // === OAuth2 로그인 설정 ===
                .oauth2Login(oauth2 -> oauth2
                        // 로그인 페이지 URL
                        .loginPage("/login")

                        // 카카오 사용자 정보 처리 서비스
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(kakaoOAuth2Service)
                        )

                        // OAuth2 로그인 성공 시 처리 핸들러
                        .successHandler(oAuth2AuthSuccessHandler)
                )

                // === 커스텀 필터 추가 ===
                // JWT 인증 필터를 Spring Security 필터 체인에 추가
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 도메인 설정 (개발환경)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",    // React 개발 서버
                "http://localhost:8080",     // 같은 서버
                "https://devseed.store"
        ));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));

        // 허용할 헤더
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 인증 정보 포함 허용 (쿠키, Authorization 헤더 등)
        configuration.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
