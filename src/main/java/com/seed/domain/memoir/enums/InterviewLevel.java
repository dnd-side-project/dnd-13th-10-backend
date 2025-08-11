package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewLevel implements EnumCode {
    VERY_EASY("10", "매우 쉬움"),
    EASY("20", "쉬움"),
    NORMAL("30", "보통"),
    HARD("40", "어려움"),
    VERY_HARD("50", "매우 어려움");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewLevel> {
        public JpaConverter() { super(InterviewLevel.class); }
    }
}
