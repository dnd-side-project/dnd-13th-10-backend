package com.seed.global.auth.service;

import com.seed.global.exception.BusinessException;
import com.seed.global.jwt.JwtUtil;
import com.seed.global.jwt.service.RefreshTokenService;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public Map<String, String> refreshToken(String refreshToken) {

        if (refreshToken == null) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_REQUIRED);
        }

        String socialId = jwtUtil.getSocialIdFromToken(refreshToken);

        if(!refreshTokenService.validateRefreshToken(socialId, refreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
        }

        String newAccessToken = jwtUtil.generateAccessToken(socialId);
        String newRefreshToken = jwtUtil.generateRefreshToken(socialId);

        refreshTokenService.rotateRefreshToken(socialId, newRefreshToken);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    public void logout(String accessToken) {

        String socialId = jwtUtil.getSocialIdFromToken(accessToken);

        refreshTokenService.deleteRefreshToken(socialId);
    }
}
