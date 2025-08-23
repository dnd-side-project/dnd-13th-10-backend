package com.seed.domain.user.controller;

import com.seed.domain.user.converter.UserConverter;
import com.seed.domain.user.dto.response.UserSearchHistResponse;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.entity.UserSearchHist;
import com.seed.domain.user.service.UserSearchHistService;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/hist")
@RequiredArgsConstructor
@Tag(name = "사용자 검색기록 API")
public class UserSearchHistController {

    private final UserSearchHistService userSearchHistService;

    @Operation(
            summary = "사용자의 검색 기록 조회 API",
            description = "사용자의 검색 기록을 조회하는 API 입니다."
    )
    @GetMapping
    public ApiResponse<List<UserSearchHistResponse>> findListUserSearchHist(@AuthenticationPrincipal User user) {
        List<UserSearchHist> listUserSearchHist = userSearchHistService.findAllByUserId(user.getId());
        List<UserSearchHistResponse> listUserSearchHistRes =
                listUserSearchHist.stream().map(UserConverter::toUserSearchHistResponse).toList();

        return ApiResponse.success(listUserSearchHistRes);
    }

    @Operation(
            summary = "사용자 검색 기록 삭제 API",
            description = "사용자 검색 기록 단건 삭제 API 입니다.",
            parameters = {
                    @Parameter(name = "id", description = "검색 기록 ID (PK)", required = true)
            }
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUserSearchHist(@AuthenticationPrincipal User user, @PathVariable("id") Long histId) {
        userSearchHistService.deleteById(user.getId(), histId);
        return ApiResponse.success();
    }

    @Operation(
            summary = "사용자 검색 기록 전체 삭제 API",
            description = "사용자 검색 기록 전체 삭제 API 입니다."
    )
    @DeleteMapping("/all")
    public ApiResponse<Void> deleteAllUserSearchHist(@AuthenticationPrincipal User user) {
        userSearchHistService.deleteAllByUserId(user.getId());
        return ApiResponse.success();
    }

}
