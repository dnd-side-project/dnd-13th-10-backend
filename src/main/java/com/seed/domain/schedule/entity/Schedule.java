package com.seed.domain.schedule.entity;

import com.seed.domain.company.entity.Company;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

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

    private String interviewPlace;

    public static Schedule ofId(Long scheduleId) {
        return Schedule.builder().id(scheduleId).build();
    }

    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    List<Memoir> memoirs = new ArrayList<>();

    public void modifySchedule(ScheduleRequest.ScheduleUpdateRequestDTO requestDTO, Company company) {
        if(requestDTO.getPosition() != null) {
            this.position = requestDTO.getPosition();
        }
        if(requestDTO.getInterviewStep() != null) {
            this.interviewStep = requestDTO.getInterviewStep();
        }
        if(requestDTO.getInterviewTime() != null) {
            this.interviewDatetime = requestDTO.getInterviewTime();
        }
        if(requestDTO.getLocation() != null) {
            this.location = requestDTO.getLocation();
        }
        if (company != null) {
            this.company = company;
        }
    }

    public void setUser(User user) {
        this.user = user;
        user.getSchedules().add(this);
    }
}
