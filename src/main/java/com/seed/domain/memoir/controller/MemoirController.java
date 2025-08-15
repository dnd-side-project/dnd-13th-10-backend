package com.seed.domain.memoir.controller;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.global.response.ApiResponse;
import com.seed.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memoir")
@RequiredArgsConstructor
public class MemoirController {

    private final MemoirService memoirService;

    /**
     * 퀵 회고 등록
     * @param quickMemoirRequest
     * @return
     */
    @PostMapping("/quick")
    public ApiResponse<Long> createQuickMemoir(@RequestBody QuickMemoirRequest quickMemoirRequest) {
        Long quickMemoir = memoirService.createQuickMemoir(quickMemoirRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_CREATED ,quickMemoir);
    }

    /**
     * 회고 리스트 조회 (퀵 회고, 일반 회고 모두 포함)
     * @return
     */
    @GetMapping
    public ApiResponse<List<MemoirListResponse>> findListMemoir() {
        List<MemoirListResponse> listMemoir = memoirService.findListMemoir();
        return ApiResponse.success(listMemoir);
    }

    /**
     * 상세 회고 조회 (퀵 회고, 일반 회고 모두 포함)
     * @param memoirId
     * @return
     */
    @GetMapping("/{memoirId}")
    public ApiResponse<MemoirResponse> findMemoirById(@PathVariable Long memoirId) {
        MemoirResponse memoirResponse = memoirService.findMemoirById(memoirId);
        return ApiResponse.success(memoirResponse);
    }
}
