package com.seed.domain.mapping.bookmark.converter;

import com.seed.domain.mapping.bookmark.dto.BookMarkResponse;
import com.seed.domain.mapping.bookmark.entity.BookMark;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;

import java.time.LocalDateTime;

public class BookMarkConverter {

    public static BookMark toBookMark(Long userId, Long memoirId) {
        return BookMark.builder()
                .user(User.builder().id(userId).build())
                .memoir(Memoir.builder().id(memoirId).build())
                .build();
    }

    public static BookMarkResponse toBookMarkResponse(Boolean isBookMarked) {
        return BookMarkResponse.builder()
                .isBookMarked(isBookMarked)
                .toggledAt(LocalDateTime.now())
                .build();
    }
}
