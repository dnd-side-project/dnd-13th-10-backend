package com.seed.domain.memoir.repository;

import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;

import java.util.List;

public interface MemoirQueryRepository {
    List<MemoirListResponse> findListMemoir();
    List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchMemoirRequest);
}
