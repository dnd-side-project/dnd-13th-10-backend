package com.seed.domain.memoir.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.seed.domain.memoir.enums.InterviewStatus;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoirListResponse {

    private Long id;
    private String type;
    private String interviewStatus;
    private String companyName;
    private String position;
    private LocalDateTime createdAt;
    private String firstQuestion;

    @QueryProjection
    public MemoirListResponse(
            Long id,
            MemoirType type,
            InterviewStatus interviewStatus,
            String companyName,
            Position position,
            LocalDateTime createdAt,
            String firstQuestion
    ) {
        this.id = id;
        this.type = type.getDescription();
        this.interviewStatus = interviewStatus.getDescription();
        this.companyName = companyName;
        this.position = position.getDescription();
        this.createdAt = createdAt;
        this.firstQuestion = firstQuestion;
    }
}

