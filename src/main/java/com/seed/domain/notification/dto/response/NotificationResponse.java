package com.seed.domain.notification.dto.response;

import com.seed.domain.notification.entity.Notification;
import com.seed.global.code.EnumCode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponse {
    private String notificationCategory;
    private String content;

    public static NotificationResponse fromEntity(Notification notification) {
        return NotificationResponse.builder()
                .notificationCategory(EnumCode.getDescriptionOrNull(notification.getNotificationCategory()))
                .content(notification.getContent())
                .build();
    }
}
