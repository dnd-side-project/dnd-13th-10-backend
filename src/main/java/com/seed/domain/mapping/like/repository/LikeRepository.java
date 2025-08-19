package com.seed.domain.mapping.like.repository;

import com.seed.domain.mapping.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndMemoirId(Long userId, Long memoirId);

    Optional<Like> findByUserIdAndMemoirId(Long userId, Long memoirId);

    @Query("select l from Like l join fetch l.memoir m where l.user.id = :userId order by l.id asc")
    List<Like> findAllByUserId(@Param("userId") Long userId);
}
