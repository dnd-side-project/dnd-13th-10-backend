package com.seed.domain.notification.service.impl;

import com.seed.domain.notification.dto.reqeust.NotificationCreateRequest;
import com.seed.domain.notification.dto.response.NotificationResponse;
import com.seed.domain.notification.entity.Notification;
import com.seed.domain.notification.enums.NotificationCategory;
import com.seed.domain.notification.repository.NotificationRepository;
import com.seed.domain.notification.service.NotificationService;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        List<Notification> listNotification = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return listNotification.stream().map(NotificationResponse::fromEntity).toList();
    }

    // 댓글 알림용
    @Override
    public Notification notifyMemoirComment(Long memoirOwnerId, Long commenterId, String companyName) {
        User owner = userService.findById(memoirOwnerId);
        User commenter = userService.findById(commenterId);

        String content = String.format("%s님이 %s님의 %s 면접 회고에 댓글을 남겼습니다.",
                commenter.getName(), owner.getName(), companyName);

        Notification notification = Notification.builder()
                .user(owner)
                .notificationCategory(NotificationCategory.COMMUNITY)
                .content(content)
                .build();

        return notificationRepository.save(notification);
    }
}
