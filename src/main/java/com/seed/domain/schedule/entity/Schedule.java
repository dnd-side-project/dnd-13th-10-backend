package com.seed.domain.schedule.entity;

import com.seed.domain.company.Company;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.schedule.dto.request.ScheduleRequest;
import com.seed.domain.schedule.enums.InterviewStep;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String position;

    @Column(length = 2)
    @Convert(converter = InterviewStep.JpaConverter.class)
    private InterviewStep interviewStep;

    private LocalDateTime interviewTime;

    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    List<Memoir> memoirs = new ArrayList<>();

    public void modifySchedule(ScheduleRequest.UpdateRequestDTO requestDTO, Company company) {
        if(requestDTO.getPosition() != null) {
            this.position = requestDTO.getPosition();
        }
        if(requestDTO.getInterviewStep() != null) {
            this.interviewStep = requestDTO.getInterviewStep();
        }
        if(requestDTO.getInterviewTime() != null) {
            this.interviewTime = requestDTO.getInterviewTime();
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
