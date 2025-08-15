package com.seed.domain.memoir.entity;

import com.seed.domain.memoir.enums.*;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.user.entity.User;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(length = 2)
    @Convert(converter = MemoirType.JpaConverter.class)
    @Comment("회고타입")
    private MemoirType type;

    @Column(length = 2)
    @Convert(converter = InterviewFormat.JpaConverter.class)
    @Comment("면접형식")
    private InterviewFormat interviewFormat;

    @Column(length = 2)
    @Convert(converter = InterviewMood.JpaConverter.class)
    @Comment("면접분위기")
    private InterviewMood interviewMood;

    @Column(length = 2)
    @Convert(converter = SatisfactionNote.JpaConverter.class)
    @Comment("만족도")
    private SatisfactionNote satisfactionNote;

    @Column(length = 2)
    @Convert(converter = InterviewLevel.JpaConverter.class)
    @Comment("난이도")
    private InterviewLevel interviewLevel;

    @Column(length = 2)
    @Convert(converter = InterviewStatus.JpaConverter.class)
    @Comment("면접상태")
    private InterviewStatus interviewStatus;

    @Column(length = 2)
    @Convert(converter = InterviewMethod.JpaConverter.class)
    @Comment("면접방식")
    private InterviewMethod interviewMethod;

    @Comment("면접진행시간")
    private int interviewDuration;

    @Column(length = 500)
    private String freeNote;

    private String interviewPlace;

    @Column(length = 500)
    private String url;

    private int helpCount;

    private int viewCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isPublic = true;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

}
