package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.entity.MemoirView;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.repository.MemoirViewRepository;
import com.seed.domain.memoir.service.MemoirViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemoirViewServiceImpl implements MemoirViewService {

    private final MemoirRepository memoirRepository;
    private final MemoirViewRepository memoirViewRepository;

    private final Clock clock = Clock.systemUTC(); // UTC 저장 권장

    @Override
    @Transactional
    public void recordView(Long memoirId, Long viewerId) {
        // FK만 필요하니 로드 안 하고 프록시 참조
        Memoir memoirRef = memoirRepository.getReferenceById(memoirId);

        memoirViewRepository.save(
            MemoirView.builder()
                .memoir(memoirRef)
                .viewerId(viewerId) // 비로그인 null 가능
                .viewedAt(LocalDateTime.now(clock)) // @CreationTimestamp 써도 됨
                .build()
        );

        // 누적 조회수는 DB에서 원자적으로 +1 (레이스 컨디션 방지)
        memoirRepository.incrementViewCount(memoirId);
    }
}
