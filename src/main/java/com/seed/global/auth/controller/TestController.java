package com.seed.global.auth.controller;

import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/api/test")
    public ApiResponse<?> test(@AuthenticationPrincipal User user) {
        String socialId = user.getSocialId();
        Long userId = user.getId();
        String email = user.getEmail();
        String name = user.getName();
        
        return ApiResponse.success("현재 로그인한 사용자 - socialId: " + socialId + ", userId: " + userId + ", name: " + name);
    }
}
