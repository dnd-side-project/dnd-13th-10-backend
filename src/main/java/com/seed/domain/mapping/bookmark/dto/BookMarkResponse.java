package com.seed.domain.mapping.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookMarkResponse {

    private boolean isBookMarked;
    private LocalDateTime toggledAt;
}
