package com.seed.domain.schedule.repository;

import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.global.paging.CursorPage;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScheduleQueryRepository {
    CursorPage<List<ScheduleResponse.ScheduleInfoDTO>> findAllSchedules(String cursor, Long userId, int size);
}
