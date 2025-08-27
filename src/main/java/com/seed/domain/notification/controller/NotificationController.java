package com.seed.domain.notification.controller;

import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.service.NotificationService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            summary = "[테스트용] 회고 알림을 생성하는 API",
            description = "시스템 알림(회고 알림)을 생성하는 테스트용 API 입니다.<br>"
                    + "- 실제로는 자동으로 스케줄을 통해 면접일정이 7일이 지난 건에 대한 알림이 자동으로 생성됩니다.<br>"
                    + "- 이외의 커뮤니티 알림 생성하는 방법은 누군가 나의 회고에 댓글을 달면 알림이 생성됩니다."
    )
    @PostMapping
    public ApiResponse<?> createNotification(
            @AuthenticationPrincipal User user,
            @RequestParam String content
            ) {
        notificationService.createNotification(user.getId(), content);
        return ApiResponse.success();
    }

}
