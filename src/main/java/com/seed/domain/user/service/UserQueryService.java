package com.seed.domain.user.service;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.response.UserInfoResponse;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserQueryService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserConverter.toUserInfoResponse(user);
    }
}
