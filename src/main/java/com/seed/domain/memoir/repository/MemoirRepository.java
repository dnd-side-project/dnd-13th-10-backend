package com.seed.domain.memoir.repository;

import com.seed.domain.memoir.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoirRepository extends JpaRepository<Memoir, Long> {

}

