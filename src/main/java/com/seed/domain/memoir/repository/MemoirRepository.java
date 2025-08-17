package com.seed.domain.memoir.repository;

import com.seed.domain.memoir.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoirRepository extends JpaRepository<Memoir, Long>, MemoirQueryRepository {
    // isPublic, isUse가 true인 데이터만 createdAt 기준 내림차순
    List<Memoir> findAllByIsPublicTrueAndIsUseTrueOrderByCreatedAtDesc();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Memoir m set m.viewCount = m.viewCount + 1 where m.id = :id")
    int incrementViewCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Memoir m set m.likeCount = m.likeCount + 1 where m.id = :memoirId")
    int incrementLikeCount(@Param("memoirId") Long memoirId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Memoir m set m.likeCount = greatest (m.likeCount - 1, 0) where m.id = :memoirId")
    int decrementLikeCount(@Param("memoirId") Long memoirId);
}

