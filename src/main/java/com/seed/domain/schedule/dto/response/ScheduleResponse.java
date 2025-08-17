package com.seed.domain.schedule.dto.response;

import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoDTO {
        private Long id;
        private String companyName;
        private Position position;
        private LocalDateTime interviewDate;
        private String interviewStep;
        private int remainDate;
        private List<MemoirType> memoirTypes;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DetailDTO {
        private Long id;
        private String companyName;
        private Position position;
        private LocalDateTime interviewDate;
        private String interviewStep;
        private String location;
    }
}
