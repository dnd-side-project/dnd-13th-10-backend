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
import org.springframework.http.ResponseCookie;
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
    
    @Value("${app.frontend-url}")
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

        // ResponseCookie를 사용하여 쿠키 설정
        response.addHeader("Set-Cookie", createCookie("refreshToken", refreshToken, 60 * 60 * 24 * 7).toString()); // 7일
        
        // 프론트엔드 페이지로 리다이렉트
        response.sendRedirect(frontendUrl + "/login/success");
    }
    
    private ResponseCookie createCookie(String name, String value, int maxAge) {
       return ResponseCookie.from(name, value)
               .httpOnly(true)
               .secure(true)
               .path("/")
               .maxAge(maxAge)
               .sameSite("Lax")
               .build();
    }
}
