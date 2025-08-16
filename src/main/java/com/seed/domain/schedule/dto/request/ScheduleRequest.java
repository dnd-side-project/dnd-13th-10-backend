package com.seed.domain.schedule.dto.request;

import com.seed.domain.schedule.enums.InterviewStep;
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
    public static class CreateRequestDTO {
        private String companyName;
        private String position;
        private LocalDateTime interviewTime;
        private String location;
        private InterviewStep interviewStep;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequestDTO {
        private String companyName;
        private String position;
        private LocalDateTime interviewTime;
        private String location;
        private InterviewStep interviewStep;
    }
}
