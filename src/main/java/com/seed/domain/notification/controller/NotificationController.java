package com.seed.domain.notification.controller;

import com.seed.domain.notification.dto.reqeust.NotificationCreateRequest;
import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.service.NotificationService;
import com.seed.domain.user.entity.User;
import com.seed.global.response.ApiResponse;
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
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ApiResponse<List<NotificationResponse>> getMyNotifications(@AuthenticationPrincipal User user) {
        List<NotificationResponse> listNotificationRes = notificationService.getUserNotifications(user.getId());
        return ApiResponse.success(listNotificationRes);
    }

    // 알림 생성 테스트용
    @PostMapping
    public ApiResponse<?> createNotification(@AuthenticationPrincipal User user, NotificationCreateRequest createRequest) {
        notificationService.createNotification(user.getId(), createRequest.getContent());
        return ApiResponse.success();
    }

}
