package com.seed.domain.memoir.repository;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.global.paging.CursorPage;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoirQueryRepository {
    CursorPage<List<MemoirListResponse>> findListMemoir(String position, String cursor, int size);

    List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchMemoirRequest);

    // 이번 주 HOT TOP 10
    List<HotMemoirListResponse> findWeeklyTop10(LocalDateTime utcStart, LocalDateTime utcEnd);

    CursorPage<List<CommonMemoirResponse>> getLikedMemoirs(Long userId, String cursor, int size);

    CursorPage<List<CommonMemoirResponse>> getBookMarkedMemoirs(Long userId, String cursor, int size);

    CursorPage<List<CommonMemoirResponse>> getCommentedMemoirs(Long userId, String cursor, int size);

}
