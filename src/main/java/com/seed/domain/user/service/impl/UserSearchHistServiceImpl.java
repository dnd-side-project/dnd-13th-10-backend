package com.seed.domain.user.service.impl;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.request.CreateUserSearchHistRequest;
import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.repository.UserSearchHistRepository;
import com.seed.domain.user.service.UserSearchHistService;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchHistServiceImpl implements UserSearchHistService {

    private final UserSearchHistRepository userSearchHistRepository;

    @Override
    public List<UserSearchHist> findAllByUserId(Long userId) {
        return userSearchHistRepository.findAllByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    @Transactional
    public void upsert(CreateUserSearchHistRequest req) {
        try {
            userSearchHistRepository.findByUserIdAndContent(req.getUserId(), req.getContent())
                    .ifPresentOrElse(
                            hist -> userSearchHistRepository.touch(hist.getId(), LocalDateTime.now()), // 존재: updatedAt만 갱신
                            () -> userSearchHistRepository.save(UserConverter.toUserSearchHist(req))  // 없음: insert
                    );
        } catch (DataIntegrityViolationException e) {
            // 동시성: 유니크 충돌 났다면 방어적으로 touch 시도
            userSearchHistRepository.findByUserIdAndContent(req.getUserId(), req.getContent())
                    .ifPresent(h -> userSearchHistRepository.touch(h.getId(), LocalDateTime.now()));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long userId, Long id) {
        UserSearchHist userSearchHist = userSearchHistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "해당 검색기록은 존재하지 않습니다."));

        if (!userSearchHist.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        userSearchHistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(Long userId) {
        userSearchHistRepository.deleteAllByUserId(userId);
    }
}
