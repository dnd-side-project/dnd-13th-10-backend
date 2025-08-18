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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Value("${cors.allowed-origins}")
    private List<String> allowedOrigins;


    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String redirectURL;

        // CORS 헤더 수동 추가
        String origin = request.getHeader("Origin");
        if (origin != null && allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 정보에서 socialId 추출 (카카오의 경우 "id" 필드)
        String socialId = oauth2User.getAttribute("id").toString();

        // JWT 토큰 생성
        String refreshToken = jwtUtil.generateRefreshToken(socialId);

        refreshTokenService.saveRefreshToken(socialId, refreshToken);

        // ResponseCookie를 사용하여 쿠키 설정
        response.addHeader("Set-Cookie", createCookie("refreshToken", refreshToken, 60 * 60 * 24 * 7).toString()); // 7일

        // 프론트엔드 페이지로 리다이렉트
        redirectURL = UriComponentsBuilder.fromUriString(frontendUrl)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }
    
    private ResponseCookie createCookie(String name, String value, int maxAge) {
       return ResponseCookie.from(name, value)
               .httpOnly(true)
               .secure(true)
               .path("/")
               .maxAge(maxAge)
               .sameSite("None")
               .build();
    }
}
