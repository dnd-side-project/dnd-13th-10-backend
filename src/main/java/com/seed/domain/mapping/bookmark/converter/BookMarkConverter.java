package com.seed.domain.mapping.bookmark.converter;

import com.seed.domain.mapping.bookmark.entity.BookMark;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

public class BookMarkConverter {

    public static BookMark toBookMark(Long userId, Long memoirId) {
        return BookMark.builder()
                .user(User.builder().id(userId).build())
                .memoir(Memoir.builder().id(memoirId).build())
                .build();
    }
}
