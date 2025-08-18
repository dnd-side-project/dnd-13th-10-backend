package com.seed.domain.comment.service;

import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
