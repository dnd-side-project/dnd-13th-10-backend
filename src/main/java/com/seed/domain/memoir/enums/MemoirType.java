package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum MemoirType implements EnumCode {
    QUICK("10", "퀵 회고"),
    GENERAL("20", "일반 회고");

    private final String code;
    private final String description;

    MemoirType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static MemoirType fromCode(String code) {
        for (MemoirType mood : values()) {
            if (mood.code.equals(code)) {
                return mood;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    // 내부 컨버터
    public static class Converter implements AttributeConverter<MemoirType, String> {
        @Override
        public String convertToDatabaseColumn(MemoirType attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public MemoirType convertToEntityAttribute(String dbData) {
            return dbData == null ? null : MemoirType.fromCode(dbData);
        }
    }
}
