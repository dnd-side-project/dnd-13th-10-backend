package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum InterviewLevel implements EnumCode {
    VERY_EASY("10", "매우 쉬움"),
    EASY("20", "쉬움"),
    NORMAL("30", "보통"),
    HARD("40", "어려움"),
    VERY_HARD("50", "매우 어려움");

    private final String code;
    private final String description;

    InterviewLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static InterviewLevel fromCode(String code) {
        for (InterviewLevel level : values()) {
            if (level.code.equals(code)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    // 내부 컨버터
    public static class Converter implements AttributeConverter<InterviewLevel, String> {
        @Override
        public String convertToDatabaseColumn(InterviewLevel attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public InterviewLevel convertToEntityAttribute(String dbData) {
            return dbData == null ? null : InterviewLevel.fromCode(dbData);
        }
    }
}
