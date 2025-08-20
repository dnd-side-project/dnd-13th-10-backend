package com.seed.domain.comment.service;

import com.seed.domain.comment.converter.CommentConverter;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public CursorPage<List<CommentResponse.InfoDTO>> getComments(Long memoirId, Long nextCursor, int size) {
        return commentRepository.getComments(memoirId, nextCursor, size);
    }

    public List<CommentResponse.InfoDTO> getComments(Long memoirId) {
        List<Comment> comments = commentRepository.findAllByMemoirIdOrderById(memoirId);

        List<CommentResponse.InfoDTO> parentComments = new ArrayList<>();

        comments.stream()
                .filter(comment -> comment.getParentId() == null)
                .forEach(comment -> {
                    CommentResponse.InfoDTO infoDTO = CommentConverter.toInfoDTO(comment);
                    parentComments.add(infoDTO);
                });

        for (CommentResponse.InfoDTO parentComment : parentComments) {
            List<CommentResponse.InfoDTO> childComments = comments.stream()
                    .filter(comment -> comment.getParentId() != null && comment.getParentId().equals(parentComment.getId()))
                    .map(CommentConverter::toInfoDTO)
                    .toList();

            parentComment.setChildren(childComments);
        }

        return parentComments;
    }

}
