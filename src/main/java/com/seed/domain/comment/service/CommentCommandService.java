package com.seed.domain.comment.service;

import com.seed.domain.comment.converter.CommentConverter;
import com.seed.domain.comment.dto.request.CommentRequest;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MemoirRepository memoirRepository;

    public void createComment(Long userId, Long memoirId, CommentRequest.CommentCreateRequestDTO request) {
        validateUserAndMemoirAndComment(userId, memoirId, request.getParentCommentId());
        commentRepository.save(CommentConverter.toComment(request, userId, memoirId));
    }

    private void validateUserAndMemoirAndComment(Long userId, Long memoirId, Long parentCommentId) {
        if(!userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "해당하는 유저가 존재하지 않습니다.");
        }

        if(!memoirRepository.existsById(memoirId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "해당하는 회고 글이 존재하지 않습니다.");
        }

        if(parentCommentId != null) {
            if(!commentRepository.existsById(parentCommentId)) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "대댓글을 작성할 댓글이 존재하지 않습니다.");
            }
        }
    }
}
