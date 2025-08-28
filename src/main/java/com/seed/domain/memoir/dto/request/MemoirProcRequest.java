package com.seed.domain.memoir.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.*;
import com.seed.domain.question.dto.request.QuestionProcRequest;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.global.code.EnumCode;
import com.seed.global.utils.DateUtil;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemoirProcRequest {
    private Long id;
    private Long scheduleId;
    private String type;
    private String interviewFormat;
    private String interviewMood;
    private String satisfactionNote;
    private String interviewLevel;
    private String interviewStatus;
    private String interviewMethod;
    private String freeNote;
    private String url;
    private String companyName;
    private String position;
    private String interviewStep;
    private String interviewDate; // yyyy-MM-dd
    private String interviewTime; // HH:mm
    private boolean isTmp = false;
    private boolean isPublic = true;
    private List<QuestionProcRequest> questions;

    @JsonProperty("isTmp")
    public boolean isTmp() {
        return isTmp;
    }

    @JsonProperty("isPublic")
    public boolean isPublic() {
        return isPublic;
    }

    public void setTmp(boolean tmp) {
        this.isTmp = tmp;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }


    public static Memoir toEntity(Long userId, MemoirProcRequest req) {
        Memoir memoir = Memoir.builder()
                .user(User.ofId(userId)) // FK 참조만 세팅
                .schedule(req.getScheduleId() == null ? null : Schedule.ofId(req.getScheduleId()))
                .type(EnumCode.valueOfCode(MemoirType.class, req.getType()))
                .interviewFormat(EnumCode.valueOfCode(InterviewFormat.class, req.getInterviewFormat()))
                .interviewMood(EnumCode.valueOfCode(InterviewMood.class, req.getInterviewMood()))
                .satisfactionNote(EnumCode.valueOfCode(SatisfactionNote.class, req.getSatisfactionNote()))
                .interviewLevel(EnumCode.valueOfCode(InterviewLevel.class, req.getInterviewLevel()))
                .interviewStatus(EnumCode.valueOfCode(InterviewStatus.class, req.getInterviewStatus()))
                .interviewMethod(EnumCode.valueOfCode(InterviewMethod.class, req.getInterviewMethod()))
                .freeNote(req.getFreeNote())
                .url(req.getUrl())
                .companyName(req.getCompanyName())
                .position(EnumCode.valueOfCode(Position.class, req.getPosition()))
                .interviewStep(EnumCode.valueOfCode(InterviewStep.class, req.getInterviewStep()))
                .interviewDatetime(DateUtil.combine(req.getInterviewDate(), req.getInterviewTime())) // yyyy-MM-dd + HH:mm → LocalDateTime
                .isTmp(req.isTmp())
                .isPublic(req.isPublic())
                .likeCount(0)
                .build();

        if(req.getScheduleId() != null) {
            memoir.setSchedule(Schedule.ofId(req.getScheduleId()));
        }

        return memoir;
    }
}
