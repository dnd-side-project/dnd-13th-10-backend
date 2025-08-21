package com.seed.domain.company.controller;

import com.seed.domain.company.dto.response.CompanyResponse;
import com.seed.domain.company.service.CompanyService;
import com.seed.global.constant.AppConstants;
import com.seed.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/search")
    public ApiResponse<List<CompanyResponse>> searchCompany(@RequestParam String keyword) {
        List<CompanyResponse> listCompanyRes = companyService.searchCompany(keyword, AppConstants.COMPANY_SUGGEST_DEFAULT_LIMIT);
        return ApiResponse.success(listCompanyRes);
    }
}
