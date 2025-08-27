package com.seed.domain.question.repository;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.global.paging.CursorPage;

import java.util.List;

public interface QuestionQueryRepository {

    CursorPage<List<QuestionResponse>> searchQuestions(
            QuestionSearchRequest searchReq, String cursor, int size
    );

}
