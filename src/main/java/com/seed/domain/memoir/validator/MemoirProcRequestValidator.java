package com.seed.domain.memoir.validator;


import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.question.dto.request.QuestionProcRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemoirProcRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemoirProcRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemoirProcRequest request = (MemoirProcRequest) target;

        // 회고 타입이 없다면 바로 에러 반환 - required, invalid, tooShort 등등...
        if (request.getType() == null) {
            errors.rejectValue("type", "required", "회고타입은 필수입니다.");
            return;
        }

        List<QuestionProcRequest> listQuestion = request.getQuestions();

        if (!request.isTmp()) {
            if (!StringUtils.hasText(request.getCompanyName())) {
                errors.rejectValue("companyName", "required", "기업명을 입력해주세요.");
            }

            if (!StringUtils.hasText(request.getPosition())) {
                errors.rejectValue("position", "required", "직무를 입력해주세요.");
            }

            if (!StringUtils.hasText(request.getInterviewDate()) || !StringUtils.hasText(request.getInterviewTime())) {
                if (!StringUtils.hasText(request.getInterviewDate())) {
                    errors.rejectValue("interviewDate", "required", "면접날짜를 입력해주세요.");
                } else {
                    errors.rejectValue("interviewTime", "required", "면접시간을 입력해주세요.");
                }
            }

            if (!StringUtils.hasText(request.getInterviewFormat())) {
                errors.rejectValue("interviewFormat", "required", "면접관 수를 선택해주세요.");
            }

            if (!StringUtils.hasText(request.getInterviewMood())) {
                errors.rejectValue("interviewMood", "required", "면접 분위기를 선택해주세요.");
            }

            if (!StringUtils.hasText(request.getSatisfactionNote())) {
                errors.rejectValue("satisfactionNote", "required", "면접 만족도를 선택해주세요.");
            }

            if (!StringUtils.hasText(request.getInterviewStatus())) {
                errors.rejectValue("interviewStatus", "required", "면접 결과를 선택해주세요.");
            }

            // if) 퀵 회고이면서 임시저장이 아니라면, else if) 일반회고이면서 임시저장이 아니라면, else) 임시저장이라면...
            if (request.getType().equals("10")) {
                for (int i = 0; i < listQuestion.size(); i++) {
                    QuestionProcRequest question = listQuestion.get(i);

                    if (!StringUtils.hasText(question.getQuestionType())) {
                        errors.rejectValue(
                                "questions[" + i + "].questionType",
                                "required",
                                "질문유형을 입력해주세요."
                        );
                    }

                    if (!StringUtils.hasText(question.getTitle())) {
                        errors.rejectValue(
                                "questions[" + i + "].title",
                                "required",
                                "질문을 입력해주세요."
                        );
                    }
                }

            } else if (request.getType().equals("20")) {
                if (!StringUtils.hasText(request.getInterviewLevel())) {
                    errors.rejectValue("interviewLevel", "required", "면접 난이도를 선택해주세요.");
                }

                for (int i = 0; i < listQuestion.size(); i++) {
                    QuestionProcRequest question = listQuestion.get(i);
                    if (!StringUtils.hasText(question.getQuestionType())) {
                        errors.rejectValue(
                                "questions[" + i + "].questionType",
                                "required",
                                "질문 유형을 입력해주세요."
                        );
                    }

                    if (!StringUtils.hasText(question.getTitle())) {
                        errors.rejectValue(
                                "questions[" + i + "].title",
                                "required",
                                "질문을 입력해주세요."
                        );
                    }

                    if (!StringUtils.hasText(question.getAnswer())) {
                        errors.rejectValue(
                                "questions[" + i + "].answer",
                                "required",
                                "질문의 답변을 입력해주세요."
                        );
                    }
                }
            }
        }
    }
}
