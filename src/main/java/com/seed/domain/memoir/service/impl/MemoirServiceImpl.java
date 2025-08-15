package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.service.MemoirService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;

    @Override
    public Long createQuickMemoir(QuickMemoirRequest quickMemoirRequest) {
        Memoir memoir = QuickMemoirRequest.toEntity(quickMemoirRequest);
        Memoir save = memoirRepository.save(memoir);
        return save.getId();
    }
}
