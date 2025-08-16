package com.seed.domain.schedule.service;

import com.seed.domain.schedule.converter.ScheduleConverter;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.repository.ScheduleRepository;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    // TODO : 회사명으로 Company 조회 후 연결 & 직무 검색 처리 방안 검토
    public void createSchedule(Long userId, ScheduleRequest.CreateRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        scheduleRepository.save(ScheduleConverter.toSchedule(requestDTO, user));
    }

    // TODO : 일정과 연결된 회고가 있을때, 회고를 우선적으로 조회 후, 삭제 또는 FK를 null 처리 후 일정 삭제
    public void deleteSchedule(Long scheduleId, Long userId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        if(!schedule.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.DELETE_FORBIDDEN);
        }

        scheduleRepository.deleteById(scheduleId);
    }

    // TODO : 회사 데이터 구축 완료 후 마무리 예정
    public void modifySchedule(Long scheduleId, Long userId, ScheduleRequest.UpdateRequestDTO requestDTO) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        if(!schedule.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.UPDATE_FORBIDDEN);
        }

        schedule.modifySchedule(requestDTO, null);
    }
}
