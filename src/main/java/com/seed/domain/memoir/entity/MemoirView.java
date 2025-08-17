package com.seed.domain.memoir.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(
        name = "memoir_view",
        indexes = {
                // 조회 집계용: memoir_id + viewed_at 범위 스캔
                @Index(name = "idx_mv_memoid_time", columnList = "memoir_id, viewed_at"),
                // 이번 주 범위 필터 전용
                @Index(name = "idx_mv_time",        columnList = "viewed_at"),
                // (선택) 특정 사용자/아이피 중복 체크/조회용
                @Index(name = "idx_mv_viewer",      columnList = "viewer_id, memoir_id, viewed_at")
        }
)
public class MemoirView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: memoir(id) (지연 로딩 권장)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "memoir_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_memoir_view_memoir"))
    private Memoir memoir;

    // 로그인 안 했으면 null
    @Column(name = "viewer_id")
    private Long viewerId;

    // 생성 시각(UTC 저장 권장). 애플리케이션에서 KST 경계 계산 후 UTC로 변환해 쿼리.
    @CreationTimestamp
    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;
}
