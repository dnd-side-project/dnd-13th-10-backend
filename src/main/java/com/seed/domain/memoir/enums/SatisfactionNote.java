package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum SatisfactionNote implements EnumCode {
    SATISFIED("10", "만족"),
    NEUTRAL("20", "보통"),
    DISSATISFIED("30", "불만족");

    private final String code;
    private final String description;

    SatisfactionNote(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static SatisfactionNote fromCode(String code) {
        for (SatisfactionNote note : values()) {
            if (note.code.equals(code)) {
                return note;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    // 내부 컨버터
    public static class Converter implements AttributeConverter<SatisfactionNote, String> {
        @Override
        public String convertToDatabaseColumn(SatisfactionNote attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public SatisfactionNote convertToEntityAttribute(String dbData) {
            return dbData == null ? null : SatisfactionNote.fromCode(dbData);
        }
    }
}
