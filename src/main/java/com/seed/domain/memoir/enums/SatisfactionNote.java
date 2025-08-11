package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SatisfactionNote implements EnumCode {
    SATISFIED("10", "만족"),
    NEUTRAL("20", "보통"),
    DISSATISFIED("30", "불만족");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<SatisfactionNote> {
        public JpaConverter() { super(SatisfactionNote.class); }
    }
}
