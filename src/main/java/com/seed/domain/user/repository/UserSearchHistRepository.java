package com.seed.domain.user.repository;

import com.seed.domain.user.entity.UserSearchHist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSearchHistRepository extends JpaRepository<UserSearchHist, Long>  {
    List<UserSearchHist> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
