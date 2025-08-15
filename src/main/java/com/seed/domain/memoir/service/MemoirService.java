package com.seed.domain.memoir.service;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;

import java.util.List;

public interface MemoirService {
    Long createQuickMemoir(QuickMemoirRequest quickMemoirRequest);
    List<MemoirListResponse> findListMemoir();
}
