package com.seed.domain.question.dto.request;

public record QuestionSearchRequest(String type, String memoirType, String condition, boolean isMine) {}
