package com.seed.global.auth.controller;

import com.seed.global.auth.dto.TokenResponseDTO;
import com.seed.global.auth.service.AuthService;
import com.seed.global.jwt.service.RefreshTokenService;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "사용자 인증 API")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "토큰 재발급 API",
            description = "쿠키에 담겨 있는 RefreshToken을 통해 토큰을 재발급합니다."
    )
    @PostMapping("/refresh")
    public ApiResponse<TokenResponseDTO> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookie(request);

        Map<String, String> tokens = authService.refreshToken(refreshToken);

        setRefreshTokenCookie(response, tokens.get("refreshToken"));

        return ApiResponse.success(TokenResponseDTO.builder()
                .accessToken(tokens.get("accessToken"))
                .build());
    }

    @Operation(
            summary = "로그아웃 API",
            description = "로그아웃을 위한 API입니다. 쿠키에 담긴 Refresh Token을 삭제합니다."
    )
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {


        String accessToken = extractAccessTokenFromHeader(request);

        authService.logout(accessToken);

        clearRefreshTokenCookie(response);

        return ApiResponse.success(null);
    }

    // Request Cookie에 담긴 Refresh Token 추출
    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true) // HTTPS에서만
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ZERO) // 즉시 만료
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
