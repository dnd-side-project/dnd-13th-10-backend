package com.seed.domain.question.service.impl;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.question.repository.QuestionRepository;
import com.seed.domain.question.service.QuestionService;
import com.seed.domain.user.dto.request.CreateUserSearchHistRequest;
import com.seed.domain.user.service.UserSearchHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserSearchHistService userSearchHistService;

    public List<QuestionResponse> searchQuestions(Long userId, QuestionSearchRequest searchReq) {

        if (StringUtils.hasText(searchReq.condition())) {
            userSearchHistService.upsert(CreateUserSearchHistRequest.builder()
                    .userId(userId).content(searchReq.condition()).build());
        }

        return questionRepository.searchQuestions(searchReq);
    }

}
