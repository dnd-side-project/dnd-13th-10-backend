package com.seed.domain.question.controller;

import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.question.service.QuestionService;
import com.seed.domain.user.entity.User;
import com.seed.global.paging.CursorPage;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Tag(name = "면접 질문 API")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(
            summary = "면접 질문 리스트 조회 API",
            description = "면접 질문 리스트 조회 API 입니다. <br>"
                    + "- 쿼리 파라미터를 통해 검색 조건을 설정합니다. *가 붙은건 필수 값입니다. <br>"
                    + "- * type(질문타입) : 10(인성), 20(직무), 30(경험), 40(회사), 50(꼬리) <br>"
                    + "- * memoirType(회고타입) : 10(퀵), 20(일반) <br>"
                    + "- condition(검색어) : 입력된 검색어가 질문 혹은 질문에 대한 답변 like 검색 <br>"
                    + "- mine(자기가 작성한 건에 대한 여부) : 기본 false, 만약 ture 값으로 주면 내가 작성한 회고에 대해서만.."
    )
    @GetMapping
    public ApiResponse<CursorPage<List<QuestionResponse>>> getAllQuestions(
            QuestionSearchRequest searchReq,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        CursorPage<List<QuestionResponse>> listQuestionResponse
                = questionService.searchQuestions(user.getId(), searchReq, cursor, size);
        return ApiResponse.success(listQuestionResponse);
    }

}
