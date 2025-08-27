package com.seed.domain.schedule.repository;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.InterviewMood;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.schedule.dto.response.ScheduleResponse;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.paging.CursorPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleQueryRepositoryImplTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MemoirRepository memoirRepository;

    @Autowired
    private UserRepository userRepository;

    User user;
    Schedule schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, schedule8, schedule9, schedule10, schedule11, schedule12;

    @BeforeEach
    void setUp() {
        user = userRepository.findById(8L).get();

//        user = User.builder()
//                .isUse(true)
//                .name("test")
//                .build();
//
//        userRepository.save(user);

        schedule1 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule2 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule3 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule4 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule5 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule6 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule7 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule8 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule9 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule10 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule11 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);
        schedule12 = createSchedule("삼성전자", InterviewStep.FINAL_INTERVIEW, "서울 반포", Position.DESIGN);

        scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, schedule8, schedule9, schedule10, schedule11, schedule12));

        Memoir memoir1 = createMemoir("삼성전자", InterviewMood.PRESSURING, Position.DESIGN, MemoirType.QUICK, schedule1);
        Memoir memoir2 = createMemoir("LG전자", InterviewMood.QUIET, Position.MARKETING_PR, MemoirType.GENERAL, schedule2);

        memoirRepository.saveAll(List.of(memoir1, memoir2));
    }


    @DisplayName("")
    @Test
    @Transactional
    @Rollback(false)
    void findAllSchedules(){

        //given
        CursorPage<List<ScheduleResponse.ScheduleInfoDTO>> allSchedules =
                scheduleRepository.findAllSchedules(null, user.getId(), 12);


        //when

        //then
//        assertThat(allSchedules).isNotNull();
//        assertThat(allSchedules.getNextCursor()).isNull();
//        assertThat(allSchedules.getPageSize()).isEqualTo(12);
//        assertThat(allSchedules.getResult().size()).isEqualTo(12);
//        assertThat(allSchedules.getResult().get(0).getId()).isEqualTo(schedule12.getId());
    }

    private Schedule createSchedule(String companyName, InterviewStep interviewStep, String location, Position position) {
        Schedule schedule = Schedule.builder()
                .companyName(companyName)
                .interviewStep(interviewStep)
                .location(location)
                .position(position)
                .user(user)
                .build();

        schedule.setUser(user);

        return schedule;
    }

    private Memoir createMemoir(String companyName, InterviewMood interviewMood, Position position,
                                MemoirType type, Schedule schedule) {
        Memoir memoir = Memoir.builder()
                .companyName(companyName)
                .interviewMood(interviewMood)
                .position(position)
                .type(type)
                .build();

        memoir.setSchedule(schedule);
        return memoir;
    }

}