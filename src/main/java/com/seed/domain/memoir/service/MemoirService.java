package com.seed.domain.memoir.service;

import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;

import java.util.List;

public interface MemoirService {
    Long createMemoir(Long userId, MemoirProcRequest memoirProcRequest);
    List<MemoirListResponse> findListMemoir();
    MemoirResponse findMemoirById(Long id);
    Long modifyMemoir(MemoirProcRequest memoirProcRequest);
    void deleteMemoir(Long id);
}
