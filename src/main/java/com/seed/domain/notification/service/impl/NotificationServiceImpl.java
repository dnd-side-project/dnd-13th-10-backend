package com.seed.domain.notification.service.impl;

import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.entity.Notification;
import com.seed.domain.notification.repository.NotificationRepository;
import com.seed.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        List<Notification> listNotification = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return listNotification.stream().map(NotificationResponse::fromEntity).toList();
    }
}
