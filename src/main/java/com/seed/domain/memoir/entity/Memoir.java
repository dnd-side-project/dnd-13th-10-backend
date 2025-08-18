package com.seed.domain.memoir.entity;

import com.seed.domain.attachment.entity.Attachment;
import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.enums.*;
import com.seed.domain.question.entity.Question;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.code.EnumCode;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

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
    private LocalDateTime interviewDatetime;

    private int likeCount;

    private int viewCount;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isTmp = false;

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

    public void removeQuestions(List<Question> qs) {
        // 삭제할 order 값만 뽑아두기
        Set<Integer> ordersToDelete = qs.stream()
                .map(Question::getDisplayOrder)
                .collect(Collectors.toSet());

        // Memoir는 영속 상태여야 함 (findById 등으로 가져온 상태)
        this.questions.removeIf(q -> ordersToDelete.contains(q.getDisplayOrder()));
    }

    public void modifyMemoirFromQuick(MemoirProcRequest req) {
        this.type = EnumCode.valueOfCode(MemoirType.class, req.getType());
        this.interviewFormat = EnumCode.valueOfCode(InterviewFormat.class, req.getInterviewFormat());
        this.interviewMood = EnumCode.valueOfCode(InterviewMood.class, req.getInterviewMood());
        this.satisfactionNote = EnumCode.valueOfCode(SatisfactionNote.class, req.getSatisfactionNote());
        this.interviewLevel = EnumCode.valueOfCode(InterviewLevel.class, req.getInterviewLevel());
        this.interviewStatus = EnumCode.valueOfCode(InterviewStatus.class, req.getInterviewStatus());
        this.interviewMethod = EnumCode.valueOfCode(InterviewMethod.class, req.getInterviewMethod());
        this.freeNote = req.getFreeNote();
        this.url = req.getUrl();
        this.companyName = req.getCompanyName();
        this.position = EnumCode.valueOfCode(Position.class, req.getPosition());
        this.interviewStep = EnumCode.valueOfCode(InterviewStep.class, req.getInterviewStep());
        this.interviewDatetime = com.seed.global.utils.DateUtil.combine(req.getInterviewDate(), req.getInterviewTime());
    }

    public void unUse() {
        if (!this.isUse) return; // 이미 비활성이면 아무 것도 안 함
        this.isUse = false;
    }

    public static Memoir ofId(Long id) {
        return Memoir.builder()
                .id(id)
                .build();
    }
}
