package com.seed.domain.user.converter;

import com.seed.domain.user.dto.response.UserSearchHistResponse;
import com.seed.domain.user.entity.UserSearchHist;

public class UserConverter {

    public static UserSearchHistResponse toUserSearchHistResponse(UserSearchHist userSearchHist) {
        return UserSearchHistResponse.builder()
                .userSearchHistId(userSearchHist.getId())
                .content(userSearchHist.getContent())
                .build();
    }

}
