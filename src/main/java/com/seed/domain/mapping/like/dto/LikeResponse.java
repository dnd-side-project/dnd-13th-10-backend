package com.seed.domain.mapping.like.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private boolean isLiked;
    private LocalDateTime toggledAt;
}
