package com.seed.domain.mapping.repository;

import com.seed.domain.mapping.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
