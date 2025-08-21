package com.seed.domain.notification.entity;

import com.seed.domain.memoir.enums.SatisfactionNote;
import com.seed.domain.notification.enums.NotificationCategory;
import com.seed.domain.user.entity.User;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2)
    @Convert(converter = NotificationCategory.JpaConverter.class)
    private NotificationCategory notificationCategory;

    private String content;

    @Column(nullable = false)
    private boolean isRead = false;
}
