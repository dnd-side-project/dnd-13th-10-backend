package com.seed.domain.company.controller;

import com.seed.domain.company.dto.response.CompanyResponse;
import com.seed.domain.company.service.CompanyService;
import com.seed.global.constant.AppConstants;
import com.seed.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
@Tag(name = "회사 API")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(
            summary = "회사 검색 API",
            description = "회사 검색 API 입니다.<br>"
                    + "- 검색어에 대한 like 검색입니다. 검색어가 2글자 이상이여야 데이터를 가지고 옵니다.",
            parameters = {
                    @Parameter(name = "keyword", description = "검색어", required = true)
            }
    )
    @GetMapping("/search")
    public ApiResponse<List<CompanyResponse>> searchCompany(@RequestParam String keyword) {
        List<CompanyResponse> listCompanyRes = companyService.searchCompany(keyword, AppConstants.COMPANY_SUGGEST_DEFAULT_LIMIT);
        return ApiResponse.success(listCompanyRes);
    }
}
