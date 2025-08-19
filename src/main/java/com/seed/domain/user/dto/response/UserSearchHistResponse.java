package com.seed.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSearchHistResponse {
    public Long userSearchHistId;
    public String content;
}
