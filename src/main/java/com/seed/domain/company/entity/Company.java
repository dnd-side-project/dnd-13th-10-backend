package com.seed.domain.company.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "company",
    indexes = {
        @Index(name = "idx_company_name", columnList = "name"),
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;

    private String name;

    private String ceoName;
}
