package com.seed.domain.mapping.like.service;

import com.seed.domain.mapping.converter.CommonMemoirConverter;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.mapping.like.entity.Like;
import com.seed.domain.mapping.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LikeQueryService {

    private final LikeRepository likeRepository;

    public List<CommonMemoirResponse> getLikedMemoirs(Long userId) {
        List<Like> likes = likeRepository.findAllByUserId(userId);

        return likes.stream()
                .map(like -> CommonMemoirConverter.fromEntity(like.getMemoir())).toList();
    }

}
