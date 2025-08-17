package com.seed.domain.mapping.bookmark.controller;

import com.seed.domain.mapping.bookmark.service.BookMarkCommandService;
import com.seed.domain.mapping.bookmark.service.BookMarkQueryService;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkCommandService bookMarkCommandService;
    private final BookMarkQueryService bookMarkQueryService;

    @PostMapping("/api/memoirs/{memoirId}/bookMark")
    public ApiResponse<?> bookMark(@PathVariable("memoirId") Long memoirId,
                                   @AuthenticationPrincipal User user) {
        return ApiResponse.success(bookMarkCommandService.toggleBookMark(user.getId(), memoirId));
    }

    @GetMapping("/api/bookMarked-memoirs")
    public ApiResponse<List<CommonMemoirResponse>> bookMarkedMemoirs(@AuthenticationPrincipal User user) {
        return ApiResponse.success(bookMarkQueryService.getBookMarkedMemoirs(user.getId()));
    }
}
