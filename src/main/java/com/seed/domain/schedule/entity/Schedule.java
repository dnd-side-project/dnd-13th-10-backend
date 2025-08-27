package com.seed.domain.schedule.entity;

import com.seed.domain.company.entity.Company;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.code.EnumCode;
import com.seed.global.entity.BaseEntity;
import com.seed.global.utils.DateUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "company_id")
//    private Company company;

    private String companyName;

    @Column(length = 2)
    @Convert(converter = Position.JpaConverter.class)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2)
    @Convert(converter = InterviewStep.JpaConverter.class)
    private InterviewStep interviewStep;

    private LocalDateTime interviewDatetime;

    public static Schedule ofId(Long scheduleId) {
        return Schedule.builder().id(scheduleId).build();
    }

    private String location;

    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    List<Memoir> memoirs = new ArrayList<>();

    public void modifySchedule(ScheduleRequest.ScheduleUpdateRequestDTO requestDTO) {
        if(requestDTO.getPosition() != null) {
            this.position = EnumCode.valueOfCode(Position.class, requestDTO.getPosition());
        }
        if(requestDTO.getInterviewStep() != null) {
            this.interviewStep = EnumCode.valueOfCode(InterviewStep.class, requestDTO.getInterviewStep());
        }
        if(requestDTO.getInterviewDate() != null && requestDTO.getInterviewTime() != null) {
            this.interviewDatetime = DateUtil.combine(requestDTO.getInterviewDate(), requestDTO.getInterviewTime());
        }
        if(requestDTO.getLocation() != null) {
            this.location = requestDTO.getLocation();
        }
        if (requestDTO.getCompanyName() != null) {
            this.companyName = requestDTO.getCompanyName();
        }
    }

    public void setUser(User user) {
        this.user = user;
        user.getSchedules().add(this);
    }
}
