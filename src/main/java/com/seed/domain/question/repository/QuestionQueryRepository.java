package com.seed.domain.question.repository;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionQueryRepository {

    List<QuestionResponse> searchQuestions(QuestionSearchRequest searchReq);

}
