package com.seed.domain.memoir.service;

import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;

import java.util.List;

public interface MemoirService {
    Long createMemoir(Long userId, MemoirProcRequest memoirProcRequest);
    List<MemoirListResponse> findListMemoir(String position);
    MemoirResponse findMemoirById(Long viewerId, Long memoirId);
    List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchMemoirRequest);
    List<MineMemoirListResponse> findListMineTmpMemoir(Long userId);
    List<HotMemoirListResponse> findWeeklyTop10();
    Long modifyMemoir(MemoirProcRequest memoirProcRequest);
    void deleteMemoir(Long id);
}
