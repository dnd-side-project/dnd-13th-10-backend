package com.seed.domain.mapping.converter;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.dto.MemoirDTO;
import com.seed.global.code.EnumCode;

public class CommonMemoirConverter {

    public static CommonMemoirResponse fromEntity(Memoir memoir) {
        return CommonMemoirResponse.builder()
                .id(memoir.getId())
                .companyName(memoir.getCompanyName())
                .createdAt(memoir.getCreatedAt())
                .type(EnumCode.getDescriptionOrNull(memoir.getType()))
                .position(EnumCode.getDescriptionOrNull(memoir.getPosition()))
                .build();
    }

    public static CommonMemoirResponse fromDto(MemoirDTO memoirDTO) {
        return CommonMemoirResponse.builder()
                .id(memoirDTO.getId())
                .companyName(memoirDTO.getCompanyName())
                .createdAt(memoirDTO.getCreatedAt())
                .type(EnumCode.getDescriptionOrNull(memoirDTO.getType()))
                .position(EnumCode.getDescriptionOrNull(memoirDTO.getPosition()))
                .build();
    }
}
