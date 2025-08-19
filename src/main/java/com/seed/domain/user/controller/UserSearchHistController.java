package com.seed.domain.user.controller;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.response.UserSearchHistResponse;
import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.service.UserSearchHistService;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/hist")
@RequiredArgsConstructor
public class UserSearchHistController {

    private final UserSearchHistService userSearchHistService;

    @GetMapping
    public ApiResponse<List<UserSearchHistResponse>> findListUserSearchHist(
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        List<UserSearchHist> listUserSearchHist = userSearchHistService.findAllByUserId(userId);
        List<UserSearchHistResponse> listUserSearchHistRes =
                listUserSearchHist.stream().map(UserConverter::toUserSearchHistResponse).toList();

        return ApiResponse.success(listUserSearchHistRes);
    }

}
