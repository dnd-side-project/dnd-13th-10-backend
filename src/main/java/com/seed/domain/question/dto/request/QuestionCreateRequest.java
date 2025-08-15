package com.seed.domain.question.dto.request;

import com.seed.domain.question.entity.Question;
import com.seed.domain.question.enums.QuestionType;
import com.seed.global.code.EnumCode;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionCreateRequest {
    private String questionType;
    private String question;
    private int order;

    public static List<Question> toEntityList(List<QuestionCreateRequest> questionCreateRequestList) {
        return questionCreateRequestList.stream().map(QuestionCreateRequest::toEntity).collect(Collectors.toList());
    }

    public static Question toEntity(QuestionCreateRequest req) {
        return Question.builder()
                .questionType(EnumCode.valueOfCode(QuestionType.class, req.getQuestionType()))
                .question(req.getQuestion())
                .displayOrder(req.getOrder())
                .build();
    }
}
