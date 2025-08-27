package com.seed.domain.comment.converter;

import com.seed.domain.comment.dto.request.CommentRequest;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentConverter {

    public static List<CommentResponse.CommentInfoDTO> toCommentListDTO(List<Comment> parents, Map<Long, List<Comment>> replies) {

        List<CommentResponse.CommentInfoDTO> commentInfoDTOS = new ArrayList<>();

        for (Comment parent : parents) {
            CommentResponse.CommentInfoDTO parentInfo = CommentConverter.toInfoDTO(parent, true);

            List<Comment> children = replies.get(parentInfo.getId());

            if(children != null) {
                List<CommentResponse.CommentInfoDTO> childrenInfos = children.stream()
                        .map(child -> CommentConverter.toInfoDTO(child, false)).toList();

                parentInfo.setChildren(childrenInfos);
            }

            commentInfoDTOS.add(parentInfo);
        }
        return commentInfoDTOS;
    }

    public static Comment toComment(CommentRequest.CommentCreateRequestDTO requestDTO, Long userId, Long memoirId) {
        return Comment.builder()
                .parentId(requestDTO.getParentCommentId())
                .content(requestDTO.getContent())
                .user(User.ofId(userId))
                .memoir(Memoir.ofId(memoirId))
                .build();
    }

    public static CommentResponse.CommentInfoDTO toInfoDTO(Comment comment, Boolean isParent) {
        return CommentResponse.CommentInfoDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getUser().getName())
                .profileImageUrl(comment.getUser().getImageUrl())
                .createdAt(comment.getCreatedAt())
                .isParent(isParent)
                .build();
    }
}
