package com.seed.domain.user.controller;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.response.UserSearchHistResponse;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.service.UserSearchHistService;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/hist")
@RequiredArgsConstructor
public class UserSearchHistController {

    private final UserSearchHistService userSearchHistService;

    @GetMapping
    public ApiResponse<List<UserSearchHistResponse>> findListUserSearchHist(@AuthenticationPrincipal User user) {
        List<UserSearchHist> listUserSearchHist = userSearchHistService.findAllByUserId(user.getId());
        List<UserSearchHistResponse> listUserSearchHistRes =
                listUserSearchHist.stream().map(UserConverter::toUserSearchHistResponse).toList();

        return ApiResponse.success(listUserSearchHistRes);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUserSearchHist(@AuthenticationPrincipal User user, @PathVariable("id") Long histId) {
        userSearchHistService.deleteById(user.getId(), histId);
        return ApiResponse.success();
    }

    @DeleteMapping("/all")
    public ApiResponse<Void> deleteAllUserSearchHist(@AuthenticationPrincipal User user) {
        userSearchHistService.deleteAllByUserId(user.getId());
        return ApiResponse.success();
    }

}
