package com.seed.domain.memoir.service;

import com.seed.domain.memoir.dto.request.QuickMemoirProcRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;

import java.util.List;

public interface MemoirService {
    Long createQuickMemoir(QuickMemoirProcRequest quickMemoirProcRequest);
    List<MemoirListResponse> findListMemoir();
    MemoirResponse findMemoirById(Long id);
    Long modifyQuickMemoir(QuickMemoirProcRequest quickMemoirProcRequest);
}
