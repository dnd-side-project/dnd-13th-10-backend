package com.seed.domain.schedule.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.converter.ScheduleConverter;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.entity.QSchedule;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepositoryImpl implements ScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CursorPage<List<ScheduleResponse.ScheduleInfoDTO>> findAllSchedules(String cursor, Long userId, int size) {
        QSchedule sd = QSchedule.schedule;

        // 기본 쿼리 세팅
        JPAQuery<Schedule> query = queryFactory.selectFrom(sd)
                .where(sd.user.id.eq(userId))
                .orderBy(sd.id.desc())
                .limit(size + 1);

        // 커서가 존재한다면
        if(cursor != null) {
            Long nextScheduleId = Long.parseLong(cursor);
            query.where(sd.id.lt(nextScheduleId));
        }

        List<Schedule> schedules = query.fetch();

        boolean hasNext = false;
        String nextCursor = null;

        // 가져온 데이터 수가 페이지 단위보다 많다면
        if (schedules.size() > size) {
            hasNext = true;
            schedules = schedules.subList(0, size);
            nextCursor = schedules.get(schedules.size() - 1).getId().toString();
        }

        Set<MemoirType> memoirTypes = new HashSet<>();
        List<ScheduleResponse.ScheduleInfoDTO> scheduleResponses = new ArrayList<>();

        schedules.forEach(schedule -> {
            schedule.getMemoirs().forEach(memoir -> memoirTypes.add(memoir.getType()));

            scheduleResponses.add(ScheduleConverter.toInfoDTO(schedule, new ArrayList<>(memoirTypes)));
        });

        return CursorPage.of(size, nextCursor, hasNext, scheduleResponses);
    }
}
