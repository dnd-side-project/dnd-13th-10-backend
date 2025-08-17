package com.seed.global.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String REFRESH_TOKEN_KEY = "refresh_token:";
    private static final long REFRESH_TOKEN_VALIDITY = 60 * 60 * 24 * 7;

    public void saveRefreshToken(String socialId, String refreshToken) {
        String key = REFRESH_TOKEN_KEY + socialId;

        stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofSeconds(REFRESH_TOKEN_VALIDITY));

        log.info("Refresh Token 저장 : {}" , socialId);
    }

    public boolean validateRefreshToken(String socialId, String refreshToken) {
        String key = REFRESH_TOKEN_KEY + socialId;

        log.info("Redis Key : {}" , key);

        log.info("Refresh Token : {}" , refreshToken);

        String storedRefreshToken = stringRedisTemplate.opsForValue().get(key);

        log.info("Stored Refresh Token  : {}" , storedRefreshToken);

        return refreshToken.equals(storedRefreshToken);
    }

    public void deleteRefreshToken(String socialId) {
        String key = REFRESH_TOKEN_KEY + socialId;
        stringRedisTemplate.delete(key);
        log.info("Refresh Token 삭제 : {}", socialId);
    }

    public void rotateRefreshToken(String socialId, String newRefreshToken) {
        deleteRefreshToken(socialId);
        saveRefreshToken(socialId, newRefreshToken);
    }
}
