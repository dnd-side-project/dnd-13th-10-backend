package com.seed.domain.mapping.like.converter;

import com.seed.domain.mapping.like.dto.LikeResponse;
import com.seed.domain.mapping.like.entity.Like;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

import java.time.LocalDateTime;

public class LikeConverter {

    public static Like toLike(Long userId, Long memoirId) {
        return Like.builder()
                .user(User.builder().id(userId).build())
                .memoir(Memoir.builder().id(memoirId).build())
                .build();
    }

    public static LikeResponse toLikeResponse(Boolean isLiked) {
        return LikeResponse.builder()
                .isLiked(isLiked)
                .toggledAt(LocalDateTime.now())
                .build();
    }
}
