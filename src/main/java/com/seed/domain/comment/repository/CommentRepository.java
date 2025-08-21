package com.seed.domain.comment.repository;

import com.seed.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {

    @Query("select c from Comment c join fetch c.user u where c.memoir.id = :memoirId")
    List<Comment> findAllByMemoirIdOrderById(@Param("memoirId") Long memoirId);

    @Query("select c from Comment c join fetch c.memoir m where c.user.id = :userId")
    List<Comment> findByUserIdWithMemoir(@Param("userId") Long userId);
}
