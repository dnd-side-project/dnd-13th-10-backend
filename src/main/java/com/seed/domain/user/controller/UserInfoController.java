package com.seed.domain.user.controller;

import com.seed.domain.user.dto.request.UserInfoUpdateRequest;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.service.UserCommandService;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "회원 정보 API")
public class UserInfoController {

    private final UserCommandService userCommandService;

    @Operation(
            summary = "프로필 정보 수정 API",
            description = "회원의 닉네임과 프로필 이미지를 수정합니다. 수정하지 않을 정보는 비워주시면 됩니다.",
            requestBody = @RequestBody(
                    description = "프로필 수정 요청 정보",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = Object.class),
                            schemaProperties = {
                                    @SchemaProperty(
                                            name = "request",
                                            schema = @Schema(implementation = UserInfoUpdateRequest.class)
                                    ),
                                    @SchemaProperty(
                                            name = "profileImage",
                                            schema = @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
                                    )
                            }
                    )
            )
    )
    @PatchMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> updateProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "회원 정보 수정 요청", required = true)
            @RequestPart(value = "request") UserInfoUpdateRequest request,
            @Parameter(description = "프로필 이미지 파일", required = true)
            @RequestPart(value = "profileImage") MultipartFile profileImage
    ) {

        // 닉네임을 변경하지 않는 경우
        if (request == null) {
            request = new UserInfoUpdateRequest();
        }

        userCommandService.updateUserInfo(user.getId(), profileImage, request);
        return ApiResponse.success("프로필 수정이 성공하였습니다.");
    }

    @Operation(
            summary = "회원 탈퇴 API",
            description = "회원 계정을 탈퇴합니다. 실제로 데이터베이스에서 삭제되는 것이 아닌 Soft Delete 되어 유저정보는 남아있지만, " +
                    "해당 유저와 관련된 정보를 다른 유저는 볼 수 없습니다."
    )
    @PatchMapping("/delete-account")
    public ApiResponse<?> deleteAccount(@AuthenticationPrincipal User user) {
        userCommandService.deleteAccount(user.getId());
        return ApiResponse.success("회원 탈퇴에 성공하였습니다.");
    }
}
