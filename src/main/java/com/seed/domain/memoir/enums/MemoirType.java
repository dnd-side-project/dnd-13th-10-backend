package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemoirType implements EnumCode {
    QUICK("10", "퀵 회고"),
    GENERAL("20", "일반 회고");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewMood> {
        public JpaConverter() { super(InterviewMood.class); }
    }
}
