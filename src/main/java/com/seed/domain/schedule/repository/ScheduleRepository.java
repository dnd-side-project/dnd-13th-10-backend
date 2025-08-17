package com.seed.domain.schedule.repository;

import com.seed.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where s.user.id = :userId order by s.id desc ")
    List<Schedule> findAllOrderByIdDesc(@Param("userId") Long userId);
}
