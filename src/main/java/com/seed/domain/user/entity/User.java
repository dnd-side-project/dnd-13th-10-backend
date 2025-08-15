package com.seed.domain.user.entity;

import com.seed.domain.company.Company;
import com.seed.domain.schedule.entity.Schedule;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String phoneNumber;

    private String email;

    private String name;

    private String imageUrl;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

}
