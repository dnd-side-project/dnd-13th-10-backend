package com.seed.domain.question.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.question.enums.QuestionType;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(length = 500)
    private String question;

    @Column(length = 500)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir")
    private Memoir memoir;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;
}
