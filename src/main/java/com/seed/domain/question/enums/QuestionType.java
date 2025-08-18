package com.seed.domain.question.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionType implements EnumCode {
    PERSONALITY("10", "인성질문"),
    JOB("20", "직무질문"),
    EXPERIENCE("30", "경험질문"),
    COMPANY("40", "회사질문"),
    FOLLOW_UP("50", "꼬리질문");

    private final String code;          // DB 저장용 코드
    private final String description;   // 화면 표기 (ko)

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<QuestionType> {
        public JpaConverter() { super(QuestionType.class); }
    }
}
