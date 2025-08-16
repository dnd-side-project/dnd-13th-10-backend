package com.seed.domain.schedule.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position implements EnumCode  {
    PLANNING_STRATEGY("10", "기획·전략"),
    MARKETING_PR("20", "마케팅·홍보"),
    ACCOUNTING_FINANCE("30", "회계·세무·재무"),
    HR("40", "인사·노무·HRD"),
    ADMIN_LEGAL("50", "총무·법무·사무"),
    DEVELOPMENT_DATA("60", "개발·데이터"),
    DESIGN("70", "디자인"),
    SALES_CUSTOMER("80", "영업·고객관리"),
    PURCHASE_LOGISTICS("90", "구매·자재·물류"),
    DRIVING_DELIVERY("100", "운전·운송·배송"),
    SERVICE("110", "서비스"),
    CONSTRUCTION_ARCHITECTURE("120", "건설·건축"),
    FINANCE_INSURANCE("130", "금융·보험"),
    PUBLIC_WELFARE("140", "공공·복지");


    private final String code;       // 코드값
    private final String description; // 설명

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<Position> {
        public JpaConverter() { super(Position.class); }
    }
}
