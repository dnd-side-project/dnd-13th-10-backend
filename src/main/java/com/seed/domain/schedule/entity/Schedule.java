package com.seed.domain.schedule.entity;

import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.global.BaseEntity;
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

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private InterviewStep interviewStep;

    private LocalDateTime interviewTime;

}
