package com.seed.domain.notification.controller;

import com.seed.domain.notification.dto.reqeust.NotificationCreateRequest;
import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.service.NotificationService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "알림 API")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "내 알림 목록 조회 API",
            description = "나에게 온 알림 목록을 조회하는 API 입니다."
    )
    @GetMapping
    public ApiResponse<List<NotificationResponse>> getMyNotifications(@AuthenticationPrincipal User user) {
        List<NotificationResponse> listNotificationRes = notificationService.getUserNotifications(user.getId());
        return ApiResponse.success(listNotificationRes);
    }

    // 알림 생성 테스트용
    @Operation(
            summary = "[테스트용] 알림을 생성하는 ",
            description = "면접 회고 리스트를 조회하는 API 입니다.<br>"
                    + "- 퀵/일반 회고 모두 조회합니다. (임시저장은 제외)"
    )
    @PostMapping
    public ApiResponse<?> createNotification(@AuthenticationPrincipal User user, NotificationCreateRequest createRequest) {
        notificationService.createNotification(user.getId(), createRequest.getContent());
        return ApiResponse.success();
    }

}
