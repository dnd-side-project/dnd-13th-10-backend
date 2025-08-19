package com.seed.domain.user.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserSearchHistRequest {
    public Long userId;
    public String content;
}
