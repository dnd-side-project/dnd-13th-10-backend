package com.seed.domain.mapping.like.converter;

import com.seed.domain.mapping.like.entity.Like;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

public class LikeConverter {

    public static Like toLike(Long userId, Long memoirId) {
        return Like.builder()
                .user(User.builder().id(userId).build())
                .memoir(Memoir.builder().id(memoirId).build())
                .build();
    }
}
