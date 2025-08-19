package com.seed.domain.comment.dto.request;

import lombok.Getter;

public class CommentRequest {

    @Getter
    public static class CreateRequestDTO {
        private Long parentCommentId;
        private String content;
    }
}
