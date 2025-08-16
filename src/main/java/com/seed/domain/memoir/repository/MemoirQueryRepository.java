package com.seed.domain.memoir.repository;

import com.seed.domain.memoir.dto.response.MemoirListResponse;

import java.util.List;

public interface MemoirQueryRepository {
    public List<MemoirListResponse> findListMemoir();
}
