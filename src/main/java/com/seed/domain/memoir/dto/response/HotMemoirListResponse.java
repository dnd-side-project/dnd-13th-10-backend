package com.seed.domain.memoir.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.enums.Position;
import com.seed.global.code.EnumCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HotMemoirListResponse {
    private Long id;
    private String type;              // description 로 변환된 값
    private String userName;
    private String companyName;
    private String position;          // description 로 변환된 값
    private String firstQuestion;
    private String imageUrl;
    private LocalDateTime createdAt;

    private int weeklyViewCount;      // ✅ 이번 주 조회수
    private int totalViewCount;       // (옵션) 누적 조회수(Memoir.viewCount)

    @QueryProjection
    public HotMemoirListResponse(
            Long id,
            MemoirType type,          // enum 그대로 받아서 description으로 변환
            String userName,
            String companyName,
            Position position,        // enum 그대로 받아서 description으로 변환
            String firstQuestion,
            String imageUrl,
            LocalDateTime createdAt,
            Long weeklyViewCount,     // v.id.count() → Long
            Integer totalViewCount    // m.viewCount (int)
    ) {
        this.id = id;
        this.type = EnumCode.getDescriptionOrNull(type);
        this.userName = userName;
        this.companyName = companyName;
        this.position = EnumCode.getDescriptionOrNull(position);
        this.firstQuestion = firstQuestion;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.weeklyViewCount = (weeklyViewCount == null) ? 0 : Math.toIntExact(weeklyViewCount);
        this.totalViewCount = (totalViewCount == null) ? 0 : totalViewCount;
    }
}
