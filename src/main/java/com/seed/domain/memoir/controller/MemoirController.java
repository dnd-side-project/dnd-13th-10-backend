package com.seed.domain.memoir.controller;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.global.response.ApiResponse;
import com.seed.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memoir")
@RequiredArgsConstructor
public class MemoirController {

    private final MemoirService memoirService;

    @PostMapping("/quick")
    public ApiResponse<Long> createQuickMemoir(@RequestBody QuickMemoirRequest quickMemoirRequest) {
        Long quickMemoir = memoirService.createQuickMemoir(quickMemoirRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_CREATED ,quickMemoir);
    }
}
