package com.seed.domain.question.dto.response;

import com.seed.domain.question.entity.Question;
import com.seed.global.code.EnumCode;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResponse {
    private Long id;
    private String questionType;
    private String content;
    private String answer;
    private int displayOrder;

    public static List<QuestionResponse> fromEntityList(List<Question> questions) {
        if (questions == null || questions.isEmpty()) return null;
        return questions.stream().map(QuestionResponse::fromEntity).collect(Collectors.toList());
    }

    public static QuestionResponse fromEntity(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .questionType(EnumCode.getDescriptionOrNull(question.getQuestionType()))
                .content(question.getContent())
                .answer(question.getAnswer())
                .displayOrder(question.getDisplayOrder())
                .build();
    }
}
