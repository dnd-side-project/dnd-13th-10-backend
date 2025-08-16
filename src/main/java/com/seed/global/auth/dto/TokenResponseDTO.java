package com.seed.global.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    private String accessToken;
}
