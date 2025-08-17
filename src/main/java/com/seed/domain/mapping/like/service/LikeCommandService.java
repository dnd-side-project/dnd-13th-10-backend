package com.seed.domain.mapping.like.service;

import com.seed.domain.mapping.like.converter.LikeConverter;
import com.seed.domain.mapping.like.entity.Like;
import com.seed.domain.mapping.like.repository.LikeRepository;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeCommandService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final MemoirRepository memoirRepository;

    // TODO : 동시성 이슈 문제.. 어떤 방식으로 해결을 할지 고민
    public boolean toggleLike(Long userId, Long memoirId) {

        boolean isLiked;

        Optional<Like> like = likeRepository.findByUserIdAndMemoirId(userId, memoirId);

        // 이미 좋아요를 누른 상태라면 -> 취소
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            memoirRepository.decrementLikeCount(memoirId);
            isLiked = false;
        }
        // 좋아요를 누르지 않은 상태라면 -> 추가
        else{
            likesUp(userId, memoirId);
            memoirRepository.incrementLikeCount(memoirId);
            isLiked = true;
        }

        return isLiked;
    }

    private void likesUp(Long userId, Long memoirId) {

        if(!userRepository.existsById(userId)){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"해당 회원 정보를 찾을 수 없습니다.");
        }

        if(!memoirRepository.existsById(memoirId)){
            throw new BusinessException(ErrorCode.NOT_FOUND, "해당 회고 정보를 찾을 수 없습니다.");
        }

        likeRepository.save(LikeConverter.toLike(userId, memoirId));
    }

}
