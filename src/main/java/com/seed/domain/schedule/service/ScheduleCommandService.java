package com.seed.domain.schedule.service;

import com.seed.domain.schedule.converter.ScheduleConverter;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.repository.ScheduleRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;

    // TODO : 회사명으로 Company 조회 후 연결 & 직무 검색 처리 방안 검토
    public void createSchedule(ScheduleRequest.CreateRequestDTO requestDTO) {
        scheduleRepository.save(ScheduleConverter.toSchedule(requestDTO));
    }

    // TODO : 일정과 연결된 회고가 있을때, 회고를 우선적으로 조회 후, 삭제 또는 FK를 null 처리 후 일정 삭제
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    // TODO : 회사 데이터 구축 완료 후 마무리 예정
    public void modifySchedule(Long scheduleId, ScheduleRequest.UpdateRequestDTO requestDTO) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        schedule.updateSchedule(requestDTO, null);
    }
}
