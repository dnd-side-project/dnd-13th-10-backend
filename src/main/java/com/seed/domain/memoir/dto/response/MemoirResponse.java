package com.seed.domain.memoir.dto.response;

import com.seed.domain.memoir.entity.Memoir;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemoirResponse {
    private Long id;
    private String type;
    private String interviewStatus;
    private String interviewMethod;
    private String companyName;
    private String position;
    private LocalDateTime createdAt;

    public static List<MemoirResponse> fromMemoirList(List<Memoir> memoirList) {
        List<MemoirResponse> memoirResponseList = new ArrayList<>();
        for (Memoir memoir : memoirList) {
            memoirResponseList.add(fromEntity(memoir));
        }

        return memoirResponseList;
    }

    public static MemoirResponse fromEntity(Memoir memoir) {
        return MemoirResponse.builder()
                .id(memoir.getId())
                .type(memoir.getType() != null ? memoir.getType().getDescription() : null)
                .interviewStatus(memoir.getInterviewStatus() != null ? memoir.getInterviewStatus().getDescription() : null)
                .interviewMethod(memoir.getInterviewMethod() != null ? memoir.getInterviewMethod().getDescription() : null)
                .companyName(memoir.getCompanyName())
                .position(memoir.getPosition() != null ? memoir.getPosition().getDescription() : null)
                .createdAt(memoir.getCreatedAt())
                .build();
    }
}
