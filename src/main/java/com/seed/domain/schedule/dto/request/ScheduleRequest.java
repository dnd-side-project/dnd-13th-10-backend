package com.seed.domain.schedule.dto.request;

import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ScheduleRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleCreateRequestDTO {
        private String companyName;
        private Position position;
        private LocalDateTime interviewDateTime;
        private String location;
        private InterviewStep interviewStep;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleUpdateRequestDTO {
        private String companyName;
        private Position position;
        private LocalDateTime interviewTime;
        private String location;
        private InterviewStep interviewStep;
    }
}
