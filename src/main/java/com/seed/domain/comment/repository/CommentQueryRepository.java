package com.seed.domain.comment.repository;

import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.global.paging.CursorPage;

import java.util.List;

public interface CommentQueryRepository {
    CursorPage<List<CommentResponse.CommentInfoDTO>> getComments(Long memoirId, String cursor, int size);
}
