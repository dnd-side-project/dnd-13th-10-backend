package com.seed.domain.mapping.like.controller;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.mapping.like.dto.LikeResponse;
import com.seed.domain.mapping.like.repository.LikeRepository;
import com.seed.domain.mapping.like.service.LikeCommandService;
import com.seed.domain.mapping.like.service.LikeQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "좋아요 API")
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;

    @Operation(
            summary = "좋아요 토글 API",
            description = "특정 회고에 대한 좋아요를 토글로 전환하는 API입니다." +
                    "이미 좋아요가 되어었다면 취소를하고 liked를 false로, " +
                    "좋아요가 되어있지 않다면 좋아요를 추가하고, liked를 true로 전달합니다."
    )
    @PostMapping("/api/memoirs/{memoirId}/likes")
    public ApiResponse<LikeResponse> toggleLike(@Parameter(name = "좋아요를 토글처리 할 회고 ID") @PathVariable Long memoirId,
                                                @AuthenticationPrincipal User user) {

        return ApiResponse.success(likeCommandService.toggleLike(user.getId(), memoirId));
    }

    @Hidden
    @Operation(
            summary = "내가 좋아요한 모든 회고 글 조회 API",
            description = "사용자가 좋아요를 누른 모든 회고 글을 조회합니다."
    )
    @GetMapping("/api/liked-memoirs")
    public ApiResponse<List<CommonMemoirResponse>> getLikedMemoirs(@AuthenticationPrincipal User user) {
        return ApiResponse.success(likeQueryService.getLikedMemoirs(user.getId()));
    }
}
