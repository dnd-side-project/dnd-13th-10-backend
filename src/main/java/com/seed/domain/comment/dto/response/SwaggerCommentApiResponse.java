package com.seed.domain.comment.dto.response;

import com.seed.global.response.ApiResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class SwaggerCommentApiResponse {
    ApiResponse<List<CommentResponse.CommentInfoDTO>> response;
}
