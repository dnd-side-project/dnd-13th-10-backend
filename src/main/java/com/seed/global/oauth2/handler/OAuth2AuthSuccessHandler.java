package com.seed.global.oauth2.handler;

import com.seed.global.jwt.JwtUtil;
import com.seed.global.jwt.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    
    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        // 사용자 정보에서 socialId 추출 (카카오의 경우 "id" 필드)
        String socialId = oauth2User.getAttribute("id").toString();
        
        // JWT 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(socialId);
        String refreshToken = jwtUtil.generateRefreshToken(socialId);

        refreshTokenService.saveRefreshToken(socialId, refreshToken);
        
        // 쿠키 생성 및 설정
        Cookie accessTokenCookie = createCookie("accessToken", accessToken, 60 * 60); // 1시간
        Cookie refreshTokenCookie = createCookie("refreshToken", refreshToken, 60 * 60 * 24 * 7); // 7일
        
        // 쿠키를 응답에 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        
        // 프론트엔드 페이지로 리다이렉트
        response.sendRedirect(frontendUrl + "/login/success");
    }
    
    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setSecure(true); // HTTPS에서만 전송 (개발환경에서는 false로 설정)
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        cookie.setMaxAge(maxAge); // 쿠키 만료 시간 (초)
        // SameSite 속성은 Spring Boot 2.6+ 에서 ResponseCookie 사용 권장
        return cookie;
    }
}
