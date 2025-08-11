package com.seed.domain.schedule.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewStep implements EnumCode {
    FIRST_INTERVIEW("10", "1차 면접"),
    SECOND_INTERVIEW("20", "2차 면접"),
    FINAL_INTERVIEW("30", "최종 면접"),
    PHONE_INTERVIEW("40", "전화 면접");

    private final String code;       // 코드값
    private final String description; // 설명

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewStep> {
        public JpaConverter() { super(InterviewStep.class); }
    }
}
