package com.seed.domain.user.repository;

import com.seed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.socialId = :socialId and u.isUse = true")
    Optional<User> findBySocialId(@Param("socialId") String socialId);

}
