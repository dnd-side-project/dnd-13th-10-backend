package com.seed.domain.schedule.service;

import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.converter.ScheduleConverter;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.repository.ScheduleRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.paging.CursorPage;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

    // TODO : 페이지네이션 구현, 회고 작성 여부에 따른 응답구조 변경 (퀵, 일반 혹은 둘 다)
    public List<ScheduleResponse.ScheduleInfoDTO> findAll(Long userId) {

        List<Schedule> schedules = scheduleRepository.findAllOrderByIdDesc(userId);

        Set<MemoirType> memoirTypes = new HashSet<>();
        List<ScheduleResponse.ScheduleInfoDTO> scheduleResponses = new ArrayList<>();

        schedules.forEach(schedule -> {
            schedule.getMemoirs().forEach(memoir -> memoirTypes.add(memoir.getType()));

            scheduleResponses.add(ScheduleConverter.toInfoDTO(schedule, new ArrayList<>(memoirTypes)));
        });

        return scheduleResponses;
    }

    public CursorPage<List<ScheduleResponse.ScheduleInfoDTO>> findAll(String cursor, Long userId, int size) {
        return scheduleRepository.findAllSchedules(cursor, userId, size);
    }

    public ScheduleResponse.DetailDTO getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        return ScheduleConverter.toDetailDTO(schedule);
    }
}
