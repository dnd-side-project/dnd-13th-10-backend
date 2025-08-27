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
        private String position;
        private String interviewDate; // yyyy-MM-dd
        private String interviewTime; // HH:mm
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
        private String interviewDate; // yyyy-MM-dd
        private String interviewTime; // HH:mm
        private String location;
        private String interviewStep;
    }
}
