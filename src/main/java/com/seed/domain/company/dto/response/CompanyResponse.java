package com.seed.domain.company.dto.response;

import com.seed.domain.company.entity.Company;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyResponse {
    private Long id;
    private String region;
    private String name;
    private String ceoName;

    public static CompanyResponse fromEntity(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .region(company.getRegion())
                .name(company.getName())
                .ceoName(company.getCeoName())
                .build();
    }
}
