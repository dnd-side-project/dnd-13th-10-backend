package com.seed.domain.mapping.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.techtag.entity.TechTag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemoirTechTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_id")
    private Memoir memoir;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techTag_id")
    private TechTag techTag;
}
