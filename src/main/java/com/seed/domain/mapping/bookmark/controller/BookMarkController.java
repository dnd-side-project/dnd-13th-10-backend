package com.seed.domain.mapping.bookmark.controller;

import com.seed.domain.mapping.bookmark.dto.BookMarkResponse;
import com.seed.domain.mapping.bookmark.service.BookMarkCommandService;
import com.seed.domain.mapping.bookmark.service.BookMarkQueryService;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "북마크 API")
public class BookMarkController {

    private final BookMarkCommandService bookMarkCommandService;
    private final BookMarkQueryService bookMarkQueryService;

    @Operation(
            summary = "북마크 토글 API",
            description = "특정 회고에 대한 북마크를 토글로 전환하는 API입니다." +
                    "이미 북마크가 되어었다면 취소를하고 bookMarked를 false로, " +
                    "북마크가 되어있지 않다면 북마크를 추가하고, bookMarked를 true로 전달합니다."
    )
    @PostMapping("/api/memoirs/{memoirId}/bookMarks")
    public ApiResponse<BookMarkResponse> bookMark(@PathVariable("memoirId") Long memoirId,
                                                  @AuthenticationPrincipal User user) {
        return ApiResponse.success(bookMarkCommandService.toggleBookMark(user.getId(), memoirId));
    }

    @Hidden
    @Operation(
            summary = "내가 북마크한 모든 회고 글 조회 API",
            description = "사용자가 북마크한 모든 회고 글을 조회합니다."
    )
    @GetMapping("/api/bookMarked-memoirs")
    public ApiResponse<List<CommonMemoirResponse>> bookMarkedMemoirs(@AuthenticationPrincipal User user) {
        return ApiResponse.success(bookMarkQueryService.getBookMarkedMemoirs(user.getId()));
    }
}
