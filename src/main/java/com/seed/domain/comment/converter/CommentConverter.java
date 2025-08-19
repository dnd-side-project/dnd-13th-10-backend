package com.seed.domain.comment.converter;

import com.seed.domain.comment.dto.request.CommentRequest;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(CommentRequest.CreateRequestDTO requestDTO, Long userId, Long memoirId) {
        return Comment.builder()
                .parentId(requestDTO.getParentCommentId())
                .content(requestDTO.getContent())
                .user(User.ofId(userId))
                .memoir(Memoir.ofId(memoirId))
                .build();
    }

    public static CommentResponse.InfoDTO toInfoDTO(Comment comment) {
        return CommentResponse.InfoDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getUser().getName())
                .profileImageUrl(comment.getUser().getImageUrl())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
