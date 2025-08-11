package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewFormat implements EnumCode {
    ONE_ON_ONE("10", "1대1 면접"),
    ONE_ON_MANY("20", "일대다 면접"),
    MANY_ON_MANY("30", "다대다 면접"),
    MANY_ON_ONE("40", "다대일 면접"),
    CALL("50", "전화 면접");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewFormat> {
        public JpaConverter() { super(InterviewFormat.class); }
    }
}
