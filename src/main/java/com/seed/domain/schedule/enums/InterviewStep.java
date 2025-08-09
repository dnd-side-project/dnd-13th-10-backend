package com.seed.domain.schedule.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum InterviewStep implements EnumCode {
    FIRST_INTERVIEW("10", "1차 면접"),
    SECOND_INTERVIEW("20", "2차 면접"),
    FINAL_INTERVIEW("30", "최종 면접"),
    PHONE_INTERVIEW("40", "전화 면접");

    private final String code;       // 코드값
    private final String description; // 설명

    InterviewStep(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static InterviewStep fromValue(String code){
        for (var s : values()) if (s.code.equals(code)) return s;
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    public static class Converter implements AttributeConverter<InterviewStep, String> {
        @Override
        public String convertToDatabaseColumn(InterviewStep attr){
            return attr == null ? null : attr.getCode();
        }
        @Override
        public InterviewStep convertToEntityAttribute(String db){
            return db == null ? null : InterviewStep.fromValue(db);
        }
    }
}
