package com.seed.domain.notification.service;

import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationService {

    // 내 알림 목록 조회
    List<NotificationResponse> getUserNotifications(Long userId);

    // 댓글용 알림 생성
    Notification notifyMemoirComment(Long memoirOwnerId, Long commenterId, String companyName);

    // 회고등록 안되어 있는 건들에 대해 알림 생성
    Notification createNotification(Long userId, String content);
}
