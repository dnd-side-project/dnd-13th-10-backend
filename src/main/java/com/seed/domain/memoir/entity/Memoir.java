package com.seed.domain.memoir.entity;

import com.seed.domain.memoir.enums.*;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private InterviewFormat interviewFormat;

    @Enumerated(EnumType.STRING)
    private InterviewMood interviewMood;

    private int interviewDuration;

    @Enumerated(EnumType.STRING)
    private SatisfactionNote satisfactionNote;

    @Column(length = 500)
    private String freeNote;

    @Enumerated(EnumType.STRING)
    private InterviewLevel interviewLevel;

    private String interviewMethod;

    private String interviewType;

    @Column(length = 500)
    private String reflection;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    @Lob
    private String memo;

    @Column(length = 500)
    private String url;

    private int helpCount;

    private int viewCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isPublic = true;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

}
