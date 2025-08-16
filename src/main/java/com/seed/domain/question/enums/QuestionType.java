package com.seed.domain.question.enums;

import com.seed.global.code.EnumCode;
import com.seed.global.entity.EnumCodeJpaConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionType implements EnumCode {
    // 10번대: 분석·예측·조직 관련
    ANALYTICAL_THINKING("11", "분석력"),
    FORESIGHT("12", "예측능력"),
    ORGANIZATION("13", "조직화 능력"),

    // 20번대: 대인관계·협상·발표
    NEGOTIATION("20", "협상 능력"),
    PRESENTATION("21", "발표력"),

    // 30번대: 전문성·리더십
    TECHNICAL_EXPERTISE("30", "기술적 전문성"),
    LEADERSHIP("31", "리더십 기술"),
    COACHING("32", "코칭"),

    // 40번대: 경영·기획
    BUSINESS_ACUMEN("40", "사업적 영민성"),
    DESIGN_SKILL("41", "디자인 능력"),
    BUDGETING("42", "예산 수립 능력"),
    PROJECT_MANAGEMENT("43", "프로젝트 관리능력"),
    WORK_ORGANIZATION("44", "업무조직화 능력"),

    // 50번대: 문제 해결
    PROBLEM_SOLVING("50", "문제해결능력"),
    DECISIVENESS("51", "결단력"),
    RESULT_ORIENTATION("52", "결과지향성"),
    DECISION_MAKING("53", "의사결정력"),

    // 60번대: 기타 공통 역량
    COMPUTER_LITERACY("60", "컴퓨터 활용능력"),
    COMMUNICATION("61", "커뮤니케이션"),
    TEAMWORK("62", "팀워크"),
    ADAPTABILITY("63", "적응력"),
    CREATIVITY("64", "창의성"),

    // 70번대: 개발자 역량
    CODING_SKILL("70", "코딩 능력"),
    SYSTEM_DESIGN("71", "시스템 설계 능력"),
    ALGORITHM_PROBLEM_SOLVING("72", "알고리즘 문제 해결"),
    DEBUGGING("73", "디버깅/트러블슈팅"),
    SOFTWARE_ARCHITECTURE("74", "소프트웨어 아키텍처 이해"),
    VERSION_CONTROL("75", "버전 관리 능력"),
    SECURITY_AWARENESS("76", "보안 인식 및 대응");


    private final String code;          // DB 저장용 코드
    private final String description;   // 화면 표기 (ko)

    @Converter(autoApply = true)
    public static class JpaConverter extends EnumCodeJpaConverter<QuestionType> {
        public JpaConverter() { super(QuestionType.class); }
    }
}
