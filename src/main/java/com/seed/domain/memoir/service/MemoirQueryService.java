package com.seed.domain.memoir.service;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemoirQueryService {

    private final MemoirRepository memoirRepository;

//    public CursorPage<List<CommonMemoirResponse>> getCommetedMemoirs(Long userId, Long cursor, int size) {
//        return memoirRepository.getMemoirCursorPage(userId, cursor, size, QueryType.COMMENT);
//    }

    public CursorPage<List<CommonMemoirResponse>> getLikedMemoirs(Long userId, String cursor, int size) {
        return memoirRepository.getLikedMemoirs(userId, cursor, size);
    }

    public CursorPage<List<CommonMemoirResponse>> getBookMarkedMemoirs(Long userId, String cursor, int size) {
        return memoirRepository.getBookMarkedMemoirs(userId, cursor, size);
    }

    public CursorPage<List<CommonMemoirResponse>> getCommentedMemoirs(Long userId, String cursor, int size) {
        return memoirRepository.getCommentedMemoirs(userId, cursor, size);
    }

}
