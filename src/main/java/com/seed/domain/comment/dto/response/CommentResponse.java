package com.seed.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentInfoDTO {
        private Long id;
        private String content;
        private String author;
        private String profileImageUrl;
        private LocalDateTime createdAt;
        private List<CommentInfoDTO> children;

        public void setChildren(List<CommentInfoDTO> children) {
            this.children = children;
        }
    }
}
