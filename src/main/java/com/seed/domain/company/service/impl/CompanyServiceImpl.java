package com.seed.domain.company.service.impl;

import com.seed.domain.company.dto.response.CompanyResponse;
import com.seed.domain.company.repository.CompanyRepository;
import com.seed.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> searchCompany(String keyword, int limit) {
        if (keyword == null || keyword.trim().length() < 2) {
            return List.of();
        }

        Pageable pageable = PageRequest.of(0, limit);
        return companyRepository.searchCompanyByName(keyword.trim(), pageable)
                .stream()
                .map(CompanyResponse::fromEntity)
                .toList();
    }
}
