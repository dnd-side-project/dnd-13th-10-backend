package com.seed.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonMemoirResponse {
    private Long id;
    private String type;
    private String companyName;
    private String position;
    private LocalDateTime createdAt;
}
