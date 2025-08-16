package com.seed.domain.user.dto.response;


import com.seed.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMemoirResponse {
    private String name;

    public static UserMemoirResponse fromEntity(User user) {
        return UserMemoirResponse.builder()
                .name(user.getName())
                .build();
    }
}
