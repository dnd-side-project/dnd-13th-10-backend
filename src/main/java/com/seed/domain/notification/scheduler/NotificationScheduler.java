package com.seed.domain.notification.scheduler;

import com.seed.domain.notification.entity.Notification;
import com.seed.domain.notification.service.NotificationService;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;
    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "0 0 7 * * *")
    public void notifyMemoirNotWrittenJob() {
        notifyMemoirNotWritten();
    }

    public void notifyMemoirNotWritten() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<Schedule> schedules =
                scheduleRepository.findByInterviewDatetimeBeforeAndMemoirsIsEmpty(sevenDaysAgo);

        for (Schedule schedule : schedules) {
            Long userId = schedule.getUser().getId();
            String companyName = schedule.getCompany().getName();

            // 며칠 지났는지 계산
            long daysElapsed = ChronoUnit.DAYS.between(schedule.getInterviewDatetime().toLocalDate(), LocalDate.now());

            String content = String.format("%s 면접을 본 지 %d일이 지났습니다. 회고를 작성해 복기하세요!", companyName, daysElapsed);

            Notification notification = notificationService.createNotification(userId, content);
            log.info("Notification Create Complete !! : {} - {}", notification.getUser().getId(), notification.getContent());
        }
    }
}
