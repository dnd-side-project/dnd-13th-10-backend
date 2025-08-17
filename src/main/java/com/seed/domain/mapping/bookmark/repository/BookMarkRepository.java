package com.seed.domain.mapping.bookmark.repository;

import com.seed.domain.mapping.bookmark.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Optional<BookMark> findByUserIdAndMemoirId(Long userId, Long memoirId);

    @Query("select b from BookMark b join fetch b.memoir m where b.user.id = :userId")
    List<BookMark> findAllByUserId(@Param("userId") Long userId);
}
