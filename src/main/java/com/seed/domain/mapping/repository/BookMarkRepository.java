package com.seed.domain.mapping.repository;

import com.seed.domain.mapping.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
}
