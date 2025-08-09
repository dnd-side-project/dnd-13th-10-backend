package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum InterviewMood implements EnumCode {
    PRESSURING("10", "압박되는"),
    COMFORTABLE("20", "편안한"),
    QUIET("30", "조용한"),
    SHARP("40", "예리한"),
    FRIENDLY("50", "친근한");

    private final String code;
    private final String description;

    InterviewMood(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static InterviewMood fromCode(String code) {
        for (InterviewMood mood : values()) {
            if (mood.code.equals(code)) {
                return mood;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    // 내부 컨버터
    public static class Converter implements AttributeConverter<InterviewMood, String> {
        @Override
        public String convertToDatabaseColumn(InterviewMood attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public InterviewMood convertToEntityAttribute(String dbData) {
            return dbData == null ? null : InterviewMood.fromCode(dbData);
        }
    }
}
