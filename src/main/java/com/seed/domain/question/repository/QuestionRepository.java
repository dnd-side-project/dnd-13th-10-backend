package com.seed.domain.question.repository;

import com.seed.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository  {

}
