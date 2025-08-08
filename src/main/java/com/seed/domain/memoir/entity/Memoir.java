package com.seed.domain.memoir.entity;

import com.seed.domain.memoir.enums.*;
import com.seed.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Memoir extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemoirType type;

    @Enumerated(EnumType.STRING)
    private InterviewFormat format;

    @Enumerated(EnumType.STRING)
    private InterviewMood mood;

    private int duration;

    @Enumerated(EnumType.STRING)
    private SatisfactionNote satisfactionNote;

    private String freeNote;

    @Enumerated(EnumType.STRING)
    private InterviewLevel interviewLevel;

    private String interviewMethod;

    private String interviewType;

    private String reflection;

    private String memo;

    private String url;

    private int helpCount;

    private int viewCount;

    private boolean isPublic;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;
}
