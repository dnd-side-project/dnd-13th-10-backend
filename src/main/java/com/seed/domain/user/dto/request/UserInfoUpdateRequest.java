package com.seed.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserInfoUpdateRequest {
    @NotBlank
    @Size(max = 8)
    private String username;
}
