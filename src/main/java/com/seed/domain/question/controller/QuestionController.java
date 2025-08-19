package com.seed.domain.question.controller;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.question.service.QuestionService;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ApiResponse<List<QuestionResponse>> getAllQuestions(QuestionSearchRequest searchReq) {
        List<QuestionResponse> listQuestionResponse = questionService.searchQuestions(searchReq);
        return ApiResponse.success(listQuestionResponse);
    }

}
