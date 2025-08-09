package com.seed.domain.schedule.entity;

import com.seed.domain.schedule.enums.InterviewStep;
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

    private String companyName;

    private String position;

    @Column(length = 2)
    @Convert(converter = InterviewStep.Converter.class)
    private InterviewStep interviewStep;

    private LocalDateTime interviewTime;

}
