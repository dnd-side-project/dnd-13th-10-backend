package com.seed.domain.notification.dto.reqeust;

import com.seed.domain.notification.entity.Notification;
import com.seed.domain.notification.enums.NotificationCategory;
import com.seed.domain.user.entity.User;
import com.seed.global.code.EnumCode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationCreateRequest {

    private Long userId;
    private String notificationCategory;
    private String content;

    private Notification toEntity() {
        return Notification.builder()
                .user(User.ofId(userId))
                .notificationCategory(EnumCode.valueOfCode(NotificationCategory.class, notificationCategory))
                .content(content)
                .build();
    }

}
