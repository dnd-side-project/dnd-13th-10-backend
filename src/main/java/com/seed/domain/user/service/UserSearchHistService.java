package com.seed.domain.user.service;

import com.seed.domain.user.entity.UserSearchHist;

import java.util.List;

public interface UserSearchHistService {
    /**
     * 특정 유저의 검색 기록 조회하기
     * TODO : 검색기록에 개수 제한을 보통 두는 것 같긴한데...?
     * @param userId
     * @return
     */
    List<UserSearchHist> findAllByUserId(Long userId);
}
