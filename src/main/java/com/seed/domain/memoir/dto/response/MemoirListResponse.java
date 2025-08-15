package com.seed.domain.memoir.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoirListResponse {

    private Long id;
    private String type;
    private String interviewStatus;
    private String interviewMethod;
    private String companyName;
    private String position;
    private LocalDateTime createdAt;
    private String firstQuestion;
}

