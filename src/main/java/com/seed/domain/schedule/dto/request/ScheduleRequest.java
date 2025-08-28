package com.seed.domain.schedule.dto.request;

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
        private String position;
        private LocalDateTime interviewDateTime;
        private String location;
        private String interviewStep;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleUpdateRequestDTO {
        private String companyName;
        private String position;
        private LocalDateTime interviewDateTime;
        private String location;
        private String interviewStep;
    }
}
