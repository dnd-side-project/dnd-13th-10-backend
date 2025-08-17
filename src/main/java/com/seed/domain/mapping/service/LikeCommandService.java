package com.seed.domain.mapping.service;

import com.seed.domain.mapping.repository.LikeRepository;
import com.seed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeCommandService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public void like(Long userId, Long memoirId) {

    }
}
