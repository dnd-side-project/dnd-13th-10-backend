package com.seed.domain.memoir.entity;

import com.seed.domain.memoir.enums.*;
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
public class Memoir extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2)
    @Convert(converter = MemoirType.JpaConverter.class)
    private MemoirType type;

    @Column(length = 2)
    @Convert(converter = InterviewFormat.JpaConverter.class)
    private InterviewFormat interviewFormat;

    @Column(length = 2)
    @Convert(converter = InterviewMood.JpaConverter.class)
    private InterviewMood interviewMood;

    private int interviewDuration;

    @Column(length = 2)
    @Convert(converter = SatisfactionNote.JpaConverter.class)
    private SatisfactionNote satisfactionNote;

    @Column(length = 500)
    private String freeNote;

    @Column(length = 2)
    @Convert(converter = InterviewLevel.JpaConverter.class)
    private InterviewLevel interviewLevel;

    private String interviewMethod;

    private String interviewType;

    private String interviewPlace;

    @Column(length = 500)
    private String reflection;

    @Column(length = 2)
    @Convert(converter = ResultStatus.JpaConverter.class)
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
