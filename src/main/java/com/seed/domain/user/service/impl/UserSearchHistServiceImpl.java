package com.seed.domain.user.service.impl;

import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.repository.UserSearchHistRepository;
import com.seed.domain.user.service.UserSearchHistService;
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
}
