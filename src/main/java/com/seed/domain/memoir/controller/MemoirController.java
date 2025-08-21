package com.seed.domain.memoir.controller;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.domain.memoir.service.MemoirQueryService;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.memoir.service.MemoirViewHistService;
import com.seed.domain.memoir.validator.MemoirProcRequestValidator;
import com.seed.domain.user.entity.User;
import com.seed.global.paging.CursorPage;
import com.seed.global.response.ApiResponse;
import com.seed.global.response.ErrorCode;
import com.seed.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.seed.global.utils.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memoirs")
@RequiredArgsConstructor
@Tag(name = "회고 API")
public class MemoirController {

    private final MemoirService memoirService;
    private final MemoirViewHistService memoirViewHistService;
    private final MemoirQueryService memoirQueryService;
    private final MemoirProcRequestValidator memoirValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memoirValidator);
    }

    /**
     * 회고 등록 (퀵 회고, 일반 회고, 임시 저장)
     *
     * @param memoirProcRequest
     * @return
     */
    @PostMapping
    public ApiResponse<?> createMemoir(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid MemoirProcRequest memoirProcRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error(ErrorCode.INVALID_PARAMETER, ValidationUtil.toErrorList(bindingResult));
        }

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
     * 내가 작성한 회고 리스트 조회 (퀵 회고, 일반 회고)
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
     * 내가 작성중인 (임시저장) 회고 조회
     *
     * @param user
     * @return
     */
    @GetMapping("/mine/tmp")
    public ApiResponse<List<MineMemoirListResponse>> findMineTmpMemoir(@AuthenticationPrincipal User user) {
        List<MineMemoirListResponse> list = memoirService.findListMineTmpMemoir(user.getId());
        return ApiResponse.success(list);
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

    @Operation(
            summary = "내가 좋아요를 한 회고 글 조회 API",
            description = "내가 좋아요를 한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/liked")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getLikedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getLikedMemoirs(user.getId(), cursor, size));
    }

    @Operation(
            summary = "내가 북마크를 한 회고 글 조회 API",
            description = "내가 북마크를 한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/bookMarked")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getBookMarkedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getBookMarkedMemoirs(user.getId(), cursor, size));
    }

    @Operation(
            summary = "내가 댓글을 작성한 회고 글 조회 API",
            description = "내가 댓글을 작성한 회고 글을 조회합니다. 커서 기반 페이지네이션이 적용되어 있습니다. 다음커서와 조회할 개수를 넘겨주시면 됩니다."
    )
    @GetMapping("/commented")
    public ApiResponse<CursorPage<List<CommonMemoirResponse>>> getCommentedMemoirs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.success(memoirQueryService.getCommentedMemoirs(user.getId(), cursor, size));
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
