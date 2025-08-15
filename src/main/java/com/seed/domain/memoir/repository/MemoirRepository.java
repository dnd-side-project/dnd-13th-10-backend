package com.seed.domain.memoir.repository;

import com.seed.domain.memoir.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoirRepository extends JpaRepository<Memoir, Long> {
    // isPublic, isUse가 true인 데이터만 createdAt 기준 내림차순
    List<Memoir> findAllByIsPublicTrueAndIsUseTrueOrderByCreatedAtDesc();
}

