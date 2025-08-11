package com.seed.domain.memoir.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultStatus implements EnumCode {
    NOT_STARTED("10", "진행전"),
    IN_PROGRESS("20", "진행중"),
    COMPLETED("30", "완료"),
    PASSED("40", "합격"),
    FAILED("50", "불합격");

    private final String code;
    private final String description;

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<ResultStatus> {
        public JpaConverter() { super(ResultStatus.class); }
    }
}
