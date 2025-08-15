package com.seed.domain.question.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.InterviewMood;
import com.seed.domain.question.enums.QuestionType;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_memoir_order", columnNames = {"memoir_id", "display_order"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_id")
    private Memoir memoir;

    @Column(length = 2)
    @Convert(converter = QuestionType.JpaConverter.class)
    private QuestionType questionType;

    @Column(length = 500)
    private String question;

    @Column(length = 500)
    private String answer;

    private int displayOrder;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

    // 연관관계 편의 메서드
    public void assignMemoir(Memoir memoir) {
        this.memoir = memoir;
    }
}
