package com.seed.domain.mapping.like.controller;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.mapping.like.repository.LikeRepository;
import com.seed.domain.mapping.like.service.LikeCommandService;
import com.seed.domain.mapping.like.service.LikeQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;

    @PostMapping("/api/memoirs/{memoirId}/likes")
    public ApiResponse<?> toggleLike(@PathVariable Long memoirId, @AuthenticationPrincipal User user) {

        return ApiResponse.success(likeCommandService.toggleLike(user.getId(), memoirId));
    }

    @GetMapping("/api/liked-memoirs")
    public ApiResponse<List<CommonMemoirResponse>> getLikedMemoirs(@AuthenticationPrincipal User user) {
        return ApiResponse.success(likeQueryService.getLikedMemoirs(user.getId()));
    }
}
