package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum InterviewFormat implements EnumCode {
    ONE_ON_ONE("10", "1대1 면접"),
    ONE_ON_MANY("20", "일대다 면접"),
    MANY_ON_MANY("30", "다대다 면접"),
    MANY_ON_ONE("40", "다대일 면접"),
    CALL("50", "전화 면접");

    private final String code;
    private final String description;

    InterviewFormat(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static InterviewFormat fromValue(String code) {
        for (InterviewFormat format : values()) {
            if (format.code.equals(code)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    public static class Converter implements AttributeConverter<InterviewFormat, String> {
        @Override
        public String convertToDatabaseColumn(InterviewFormat attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public InterviewFormat convertToEntityAttribute(String dbData) {
            return dbData == null ? null : InterviewFormat.fromValue(dbData);
        }
    }
}
