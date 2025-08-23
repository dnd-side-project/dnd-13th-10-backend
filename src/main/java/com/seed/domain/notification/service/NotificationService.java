package com.seed.domain.notification.service;

import com.seed.domain.notification.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    // 내 알림 목록 조회
    List<NotificationResponse> getUserNotifications(Long userId);

}
