package com.seed.domain.attachment.entity;

import com.seed.domain.memoir.entity.Memoir;
import com.seed.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_id")
    private Memoir memoir;

    private String originalName;

    private String savedFileName;

    private String filePath;

}
