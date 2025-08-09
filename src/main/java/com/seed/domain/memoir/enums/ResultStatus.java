package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;

public enum ResultStatus implements EnumCode {
    NOT_STARTED("10", "진행전"),
    IN_PROGRESS("20", "진행중"),
    COMPLETED("30", "완료"),
    PASSED("40", "합격"),
    FAILED("50", "불합격");

    private final String code;
    private final String description;

    ResultStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getDescription() { return description; }

    public static ResultStatus fromCode(String code) {
        for (ResultStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    // 내부 컨버터
    public static class Converter implements AttributeConverter<ResultStatus, String> {
        @Override
        public String convertToDatabaseColumn(ResultStatus attribute) {
            return attribute == null ? null : attribute.getCode();
        }

        @Override
        public ResultStatus convertToEntityAttribute(String dbData) {
            return dbData == null ? null : ResultStatus.fromCode(dbData);
        }
    }
}
