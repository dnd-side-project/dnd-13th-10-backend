package com.seed.domain.memoir.dto.response;

import com.seed.domain.attachment.entity.Attachment;
import com.seed.domain.attachment.response.AttachmentResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.*;
import com.seed.domain.schedule.enums.InterviewStep;
import com.seed.domain.schedule.enums.Position;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HotMemoirResponse {
    private Long id;
    private List<AttachmentResponse> attachments;
    private MemoirType type;
    private InterviewFormat interviewFormat;
    private InterviewMood interviewMood;
    private SatisfactionNote satisfactionNote;
    private InterviewLevel interviewLevel;
    private InterviewStatus interviewStatus;
    private InterviewMethod interviewMethod;
    private String freeNote;
    private String url;
    private String companyName;
    private Position position;
    private InterviewStep interviewStep;
    private LocalDateTime interviewDatetime;
    private int likeCount;
    private int viewCount;

    public static List<HotMemoirResponse> fromMemoirList(List<Memoir> memoirList) {
        List<HotMemoirResponse> memoirResponseList = new ArrayList<>();
        for (Memoir memoir : memoirList) {
            memoirResponseList.add(fromEntity(memoir));
        }

        return memoirResponseList;
    }

    public static HotMemoirResponse fromEntity(Memoir memoir) {
        return HotMemoirResponse.builder()
                .id(memoir.getId())
                .attachments(AttachmentResponse.fromEntityList(memoir.getAttachments()))
                .type(memoir.getType())
                .interviewFormat(memoir.getInterviewFormat())
                .interviewMood(memoir.getInterviewMood())
                .satisfactionNote(memoir.getSatisfactionNote())
                .interviewLevel(memoir.getInterviewLevel())
                .interviewStatus(memoir.getInterviewStatus())
                .interviewMethod(memoir.getInterviewMethod())
                .freeNote(memoir.getFreeNote())
                .url(memoir.getUrl())
                .companyName(memoir.getCompanyName())
                .position(memoir.getPosition())
                .interviewStep(memoir.getInterviewStep())
                .interviewDatetime(memoir.getInterviewDatetime())
                .likeCount(memoir.getLikeCount())
                .viewCount(memoir.getViewCount())
                .build();
    }
}
