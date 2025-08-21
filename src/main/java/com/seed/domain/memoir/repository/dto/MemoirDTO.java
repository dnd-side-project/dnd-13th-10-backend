package com.seed.domain.memoir.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.schedule.enums.Position;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoirDTO {
    private Long id;
    private MemoirType type;
    private String companyName;
    private Position position;
    private LocalDateTime createdAt;
    private Long actionId;

    @QueryProjection
    public MemoirDTO(Long id, MemoirType type, String companyName, Position position, LocalDateTime createdAt, Long actionId) {
        this.id = id;
        this.type = type;
        this.companyName = companyName;
        this.position = position;
        this.createdAt = createdAt;
        this.actionId = actionId;
    }
}
