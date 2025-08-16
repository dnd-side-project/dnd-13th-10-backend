package com.seed.global.auth.controller;

import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public ApiResponse<?> test() {
        return ApiResponse.success("success");
    }
}
