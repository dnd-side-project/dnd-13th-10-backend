package com.seed.domain.notification.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationCategory implements EnumCode {

    SYSTEM("10", "회고알림"),
    COMMUNITY("20", "커뮤니티");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<NotificationCategory> {
        public JpaConverter() { super(NotificationCategory.class); }
    }

}
