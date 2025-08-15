package com.seed.domain.memoir.entity;

import com.seed.domain.attachment.entity.Attachment;
import com.seed.domain.memoir.enums.*;
import com.seed.domain.question.entity.Question;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "memoir", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "memoir", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

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

    @Column(length = 500)
    private String freeNote;

    @Column(length = 500)
    private String url;

    private String companyName;

    @Column(length = 2)
    @Convert(converter = Position.JpaConverter.class)
    @Comment("직무")
    private Position position;

    @Column(length = 2)
    @Convert(converter = InterviewStep.JpaConverter.class)
    @Comment("면접단계")
    private InterviewStep interviewStep;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime interviewTime;

    private int likeCount;

    private int viewCount;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isPublic = true;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

    public void addQuestions(List<Question> qs) {
        if (qs == null) return;
        for (Question q : qs) {
            this.questions.add(q);
            q.assignMemoir(this); // ★ FK(자식 쪽)도 세팅
        }
    }
}
