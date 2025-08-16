package com.seed.domain.schedule.entity;

import com.seed.domain.company.Company;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(length = 2)
    @Convert(converter = InterviewStep.JpaConverter.class)
    private InterviewStep interviewStep;

    private LocalDateTime interviewDatetime;

    private String interviewPlace;

    public static Schedule ofId(Long scheduleId) {
        return Schedule.builder().id(scheduleId).build();
    }

}
