package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewStatus implements EnumCode {
    PASS("10", "합격"),
    FAIL("20", "불합격"),
    PENDING("30", "결과 대기중");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewStatus> {
        public JpaConverter() { super(InterviewStatus.class); }
    }
}
