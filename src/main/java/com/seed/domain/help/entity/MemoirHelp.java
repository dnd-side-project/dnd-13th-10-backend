package com.seed.domain.help.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.user.entity.User;
import com.seed.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.h2.command.dml.Help;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemoirHelp extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir")
    private Memoir memoir;
}
