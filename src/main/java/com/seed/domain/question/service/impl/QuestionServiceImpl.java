package com.seed.domain.question.service.impl;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.question.repository.QuestionRepository;
import com.seed.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponse> searchQuestions(QuestionSearchRequest searchReq) {
        return questionRepository.searchQuestions(searchReq);
    }

}
