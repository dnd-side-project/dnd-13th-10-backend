package com.seed.domain.memoir.controller;

import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.memoir.service.MemoirViewHistService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import com.seed.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memoirs")
@RequiredArgsConstructor
public class MemoirController {

    private final MemoirService memoirService;
    private final MemoirViewHistService memoirViewHistService;

    /**
     * 회고 등록 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param memoirProcRequest
     * @return
     */
    @PostMapping
    public ApiResponse<Long> createMemoir(@AuthenticationPrincipal User user, @RequestBody MemoirProcRequest memoirProcRequest) {
        Long quickMemoir = memoirService.createMemoir(user.getId(), memoirProcRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_CREATED, quickMemoir);
    }

    /**
     * 회고 리스트 조회 (퀵 회고, 일반 회고 모두 포함)
     *
     * @return
     */
    @GetMapping
    public ApiResponse<List<MemoirListResponse>> findListMemoir() {
        List<MemoirListResponse> listMemoir = memoirService.findListMemoir();
        return ApiResponse.success(listMemoir);
    }

    /**
     * 상세 회고 조회 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param memoirId
     * @return
     */
    @GetMapping("/{memoirId}")
    public ApiResponse<MemoirResponse> findMemoirById(
            @PathVariable Long memoirId,
            @AuthenticationPrincipal(expression = "id") Long viewerId
    ) {
        // 조회 이벤트 기록 + viewCount 증가
        memoirViewHistService.recordView(memoirId, viewerId);

        MemoirResponse memoirResponse = memoirService.findMemoirById(viewerId, memoirId);
        return ApiResponse.success(memoirResponse);
    }

    /**
     * 내가 작성한 회고 리스트 조회 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param user
     * @param request
     * @return
     */
    @GetMapping("/mine")
    public ApiResponse<List<MineMemoirListResponse>> findMineMemoir(
            @AuthenticationPrincipal User user,
            SearchMemoirRequest request
    ) {
        List<MineMemoirListResponse> listMineMemoir = memoirService.findListMineMemoir(user.getId(), request);
        return ApiResponse.success(listMineMemoir);
    }

    /**
     * 이번주 핫 회고 TOP 10 조회
     *
     * @return
     */
    @GetMapping("/hot")
    public ApiResponse<List<HotMemoirListResponse>> findWeeklyTop10() {
        List<HotMemoirListResponse> listHotMemoir = memoirService.findWeeklyTop10();
        return ApiResponse.success(listHotMemoir);
    }

    /**
     * 회고 수정 (퀵 회고, 일반 회고)
     *
     * @param memoirProcRequest
     * @return
     */
    @PutMapping
    public ApiResponse<Long> modifyMemoir(@RequestBody MemoirProcRequest memoirProcRequest) {
        Long id = memoirService.modifyMemoir(memoirProcRequest);
        return ApiResponse.success(SuccessCode.RETROSPECT_UPDATED, id);
    }

    /**
     * 회고 삭제 (unUse)
     *
     * @param memoirId
     * @return
     */
    @DeleteMapping("/{memoirId}")
    public ApiResponse<Long> deleteMemoir(@PathVariable Long memoirId) {
        memoirService.deleteMemoir(memoirId);
        return ApiResponse.success(SuccessCode.RETROSPECT_DELETED, null);
    }
}
