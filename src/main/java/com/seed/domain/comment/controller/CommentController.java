package com.seed.domain.comment.controller;

import com.seed.domain.comment.dto.request.CommentRequest;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.dto.response.SwaggerCommentApiResponse;
import com.seed.domain.comment.service.CommentCommandService;
import com.seed.domain.comment.service.CommentQueryService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memoirs/{memoirId}/comments")
@Tag(name = "댓글 API")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping("")
    @Operation(
            summary = "댓글 작성 API",
            description = "댓글을 작성하는 API입니다. 댓글을 작성할 회고의 ID와 내용을 입력해주세요. " +
                    "대댓글의 경우, 대댓글을 달 댓글의 ID를 담아서 넘겨주세요."
    )
    public ApiResponse<?> addComment(
            @PathVariable Long memoirId,
            @AuthenticationPrincipal User user,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CommentRequest.CreateRequestDTO.class))
            ) CommentRequest.CreateRequestDTO requestDTO
    ) {
        commentCommandService.createComment(user.getId(), memoirId, requestDTO);
        return ApiResponse.success("댓글이 생성되었습니다.");
    }

    @GetMapping("")
    @Operation(
            summary = "댓글 목록 조회 API",
            description = "특정 회고의 달린 모든 댓글 목록을 조회합니다. 최신 댓글이 항상 마지막에 위치합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    content = @Content(schema = @Schema(implementation = SwaggerCommentApiResponse.class))
            )
    })
    public ApiResponse<List<CommentResponse.InfoDTO>> getComments(
            @PathVariable Long memoirId
    ) {
        return ApiResponse.success(commentQueryService.getComments(memoirId));
    }
}
