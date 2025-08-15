package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewMethod implements EnumCode {
    FACE_TO_FACE("10", "대면"),
    NON_FACE_TO_FACE("20", "비대면");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewMethod> {
        public JpaConverter() { super(InterviewMethod.class); }
    }
}
