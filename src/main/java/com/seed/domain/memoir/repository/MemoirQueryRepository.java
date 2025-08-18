package com.seed.domain.memoir.repository;

import com.querydsl.core.Tuple;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoirQueryRepository {
    List<MemoirListResponse> findListMemoir();

    List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchMemoirRequest);

    // 이번 주 HOT TOP 10
    List<HotMemoirListResponse> findWeeklyTop10(LocalDateTime utcStart, LocalDateTime utcEnd);
}
