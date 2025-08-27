package com.seed.domain.schedule.converter;

import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.code.EnumCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ScheduleConverter {

    public static Schedule toSchedule(ScheduleRequest.ScheduleCreateRequestDTO requestDTO, User user) {
        Schedule schedule = Schedule.builder()
                .position(EnumCode.valueOfCode(Position.class, requestDTO.getPosition()))
                .interviewDatetime(requestDTO.getInterviewDateTime())
                .interviewStep(EnumCode.valueOfCode(InterviewStep.class, requestDTO.getInterviewStep()))
                .location(requestDTO.getLocation())
                .companyName(requestDTO.getCompanyName())
                .build();
        schedule.setUser(user);

        return schedule;
    }

    public static ScheduleResponse.ScheduleInfoDTO toInfoDTO(Schedule schedule, List<MemoirType> memoirTypes) {
        // 현재 날짜부터 면접 날짜까지 남은 일수 계산
        int remainDays = calculateRemainDays(schedule.getInterviewDatetime());

        return ScheduleResponse.ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .position(EnumCode.getDescriptionOrNull(schedule.getPosition()))
                .companyName(schedule.getCompanyName())
                .interviewDate(schedule.getInterviewDatetime())
                .remainDate(remainDays)
                .createdAt(schedule.getCreatedAt())
                .interviewStep(schedule.getInterviewStep().getDescription())
                .memoirTypes(memoirTypes)
                .build();
    }

    public static ScheduleResponse.DetailDTO toDetailDTO(Schedule schedule) {
        return ScheduleResponse.DetailDTO.builder()
                .id(schedule.getId())
                .position(EnumCode.getDescriptionOrNull(schedule.getPosition()))
                .companyName(schedule.getCompanyName())
                .interviewDate(schedule.getInterviewDatetime())
                .interviewStep(schedule.getInterviewStep().getDescription())
                .location(schedule.getLocation())
                .build();
    }
    
    /**
     * 현재 날짜부터 면접 날짜까지 남은 일수 계산
     * @param interviewTime 면접 일시
     * @return 남은 일수 (음수면 면접이 지난 상태)
     */
    private static int calculateRemainDays(LocalDateTime interviewTime) {
        if (interviewTime == null) {
            return 0;
        }
        
        LocalDate today = LocalDate.now();
        LocalDate interviewDate = interviewTime.toLocalDate();
        
       int remainDate = (int) ChronoUnit.DAYS.between(today, interviewDate);

        if (remainDate < 0) {
            remainDate = 0;
        }
        return remainDate;
    }
}
