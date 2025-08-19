package com.seed.domain.user.service.impl;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.request.CreateUserSearchHistRequest;
import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.repository.UserSearchHistRepository;
import com.seed.domain.user.service.UserSearchHistService;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchHistServiceImpl implements UserSearchHistService {

    private final UserSearchHistRepository userSearchHistRepository;

    @Override
    public List<UserSearchHist> findAllByUserId(Long userId) {
        return userSearchHistRepository.findAllByUserId(userId);
    }

    @Override
    public void save(CreateUserSearchHistRequest createUserSearchHistRequest) {
        UserSearchHist userSearchHist = UserConverter.toUserSearchHist(createUserSearchHistRequest);
        userSearchHistRepository.save(userSearchHist);
    }

    @Override
    public void deleteById(Long userId, Long id) {
        UserSearchHist userSearchHist = userSearchHistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "해당 검색기록은 존재하지 않습니다."));

        if (!userSearchHist.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        userSearchHistRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        userSearchHistRepository.deleteAllByUserId(userId);
    }
}
