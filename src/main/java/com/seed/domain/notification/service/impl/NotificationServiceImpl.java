package com.seed.domain.notification.service.impl;

import com.seed.domain.notification.repository.NotificationRepository;
import com.seed.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

}
