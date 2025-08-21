package com.seed.domain.user.repository;

import com.seed.domain.user.entity.UserSearchHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserSearchHistRepository extends JpaRepository<UserSearchHist, Long>  {
    List<UserSearchHist> findAllByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<UserSearchHist> findByUserIdAndContent(Long userId, String content);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserSearchHist h set h.updatedAt = :now where h.id = :id")
    void touch(@Param("id") Long id, @Param("now") LocalDateTime now);

    void deleteAllByUserId(Long userId);
}
