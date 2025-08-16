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
public class QuestionProcRequest {
    private Long id;
    private String questionType;
    private String content;
    private String answer;
    private int order;

    public static List<Question> toEntityList(List<QuestionProcRequest> questionProcRequestList) {
        return questionProcRequestList.stream().map(QuestionProcRequest::toEntity).collect(Collectors.toList());
    }

    public static Question toEntity(QuestionProcRequest req) {
        return Question.builder()
                .id(req.getId())
                .questionType(EnumCode.valueOfCode(QuestionType.class, req.getQuestionType()))
                .content(req.getContent())
                .answer(req.getAnswer())
                .displayOrder(req.getOrder())
                .build();
    }
}
