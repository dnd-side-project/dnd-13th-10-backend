package com.seed.domain.memoir.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.global.code.EnumCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MineMemoirListResponse {

    private Long id;
    private String type;
    private String companyName;
    private String position;
    @Getter(AccessLevel.NONE)
    private boolean isTmp;
    @Getter(AccessLevel.NONE)
    private boolean isPublic;
    private LocalDateTime createdAt;

    @JsonProperty("isTmp")
    public boolean getIsTmp() {
        return isTmp;
    }

    @JsonProperty("isPublic")
    public boolean getIsPublic() {
        return isPublic;
    }

    public static MineMemoirListResponse fromEntity(Memoir memoir) {
        return MineMemoirListResponse.builder()
                .id(memoir.getId())
                .type(EnumCode.getDescriptionOrNull(memoir.getType()))
                .companyName(memoir.getCompanyName())
                .position(EnumCode.getDescriptionOrNull(memoir.getPosition()))
                .isTmp(memoir.isTmp())
                .isPublic(memoir.isPublic())
                .createdAt(memoir.getCreatedAt())
                .build();
    }

}
