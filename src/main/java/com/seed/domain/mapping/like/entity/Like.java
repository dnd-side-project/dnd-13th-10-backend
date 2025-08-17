package com.seed.domain.mapping.like.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "memoir_like",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "memoir_id"}))
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_id")
    private Memoir memoir;
}
