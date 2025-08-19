package com.seed.domain.question.service;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {

    /**
     * 검색 조건을 가지고 질문 검색
     *
     * @param searchReq
     * @return
     */
    List<QuestionResponse> searchQuestions(QuestionSearchRequest searchReq);

}
