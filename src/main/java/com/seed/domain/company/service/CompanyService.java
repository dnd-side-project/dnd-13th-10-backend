package com.seed.domain.company.service;

import com.seed.domain.company.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    List<CompanyResponse> searchCompany(String keyword, int limit);
}
