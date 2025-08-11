package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewMood implements EnumCode {
    PRESSURING("10", "압박되는"),
    COMFORTABLE("20", "편안한"),
    QUIET("30", "조용한"),
    SHARP("40", "예리한"),
    FRIENDLY("50", "친근한");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<InterviewMood> {
        public JpaConverter() { super(InterviewMood.class); }
    }
}
