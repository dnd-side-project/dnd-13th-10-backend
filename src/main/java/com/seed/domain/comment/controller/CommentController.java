package com.seed.domain.comment.controller;

import com.seed.domain.comment.dto.request.CommentRequest;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.domain.comment.service.CommentCommandService;
import com.seed.domain.comment.service.CommentQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.paging.CursorPage;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memoirs/{memoirId}/comments")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ApiResponse<?> addComment(
            @PathVariable Long memoirId,
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequest.CreateRequestDTO requestDTO
    ) {
        commentCommandService.createComment(user.getId(), memoirId, requestDTO);
        return ApiResponse.success("댓글이 생성되었습니다.");
    }

    @GetMapping
    public ApiResponse<CursorPage<List<CommentResponse.InfoDTO>>> getComments(
            @PathVariable Long memoirId,
            @RequestParam(name = "cursor", required = false) Long cursor,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ApiResponse.success(commentQueryService.getComments(memoirId, cursor, size));
    }
}
