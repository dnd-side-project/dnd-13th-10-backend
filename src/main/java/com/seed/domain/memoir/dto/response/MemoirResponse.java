package com.seed.domain.memoir.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed.domain.attachment.response.AttachmentResponse;
import com.seed.domain.memoir.entity.Memoir;

import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.user.dto.response.UserMemoirResponse;
import com.seed.global.code.EnumCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemoirResponse {
    private Long id;
    private UserMemoirResponse user;
    private Long scheduleId; // TODO : 상황보고 DTO 로 바꿔야할지도...

    private List<AttachmentResponse> attachments;
    private List<QuestionResponse> questions;

    // Enum → String (description 전달)
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

    // Enum → String (description 전달)
    private String position;
    private String interviewStep;

    private LocalDateTime interviewDatetime;
    private int likeCount;
    private int viewCount;
    @Getter(AccessLevel.NONE)        // Lombok이 isTmp() 안 만들게
    private boolean isTmp;
    @Getter(AccessLevel.NONE)        // Lombok이 isPublic() 안 만들게
    private boolean isPublic;
    private LocalDateTime createdAt;

    @JsonProperty("isTmp")        // JSON 키를 'isTmp'로 고정
    public boolean getIsTmp() {
        return isTmp;
    }

    @JsonProperty("isPublic")        // JSON 키를 'isPublic'로 고정
    public boolean getIsPublic() {
        return isPublic;
    }

    public static MemoirResponse fromEntity(Memoir memoir) {
        return MemoirResponse.builder()
                .id(memoir.getId())
                .user(UserMemoirResponse.fromEntity(memoir.getUser()))
                .scheduleId(memoir.getSchedule() == null ? null : memoir.getSchedule().getId())
                .attachments(AttachmentResponse.fromEntityList(memoir.getAttachments()))
                .questions(QuestionResponse.fromEntityList(memoir.getQuestions()))
                .type(EnumCode.getDescriptionOrNull(memoir.getType()))
                .interviewFormat(EnumCode.getDescriptionOrNull(memoir.getInterviewFormat()))
                .interviewMood(EnumCode.getDescriptionOrNull(memoir.getInterviewMood()))
                .satisfactionNote(EnumCode.getDescriptionOrNull(memoir.getSatisfactionNote()))
                .interviewLevel(EnumCode.getDescriptionOrNull(memoir.getInterviewLevel()))
                .interviewStatus(EnumCode.getDescriptionOrNull(memoir.getInterviewStatus()))
                .interviewMethod(EnumCode.getDescriptionOrNull(memoir.getInterviewMethod()))
                .freeNote(memoir.getFreeNote())
                .url(memoir.getUrl())
                .companyName(memoir.getCompanyName())
                .position(EnumCode.getDescriptionOrNull(memoir.getPosition()))
                .interviewStep(EnumCode.getDescriptionOrNull(memoir.getInterviewStep()))
                .interviewDatetime(memoir.getInterviewDatetime())
                .likeCount(memoir.getLikeCount())
                .viewCount(memoir.getViewCount())
                .isTmp(memoir.isTmp())
                .isPublic(memoir.isPublic())
                .createdAt(memoir.getCreatedAt())
                .build();
    }
}
