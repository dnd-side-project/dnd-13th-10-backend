package com.seed.global.oauth2.kakao.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoUserInfoDTO {

    private String id;
    private String nickname;

    public KakaoUserInfoDTO(Map<String, Object> attributes) {
        this.id = String.valueOf(attributes.get("id"));
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        this.nickname = (String) properties.get("nickname");
    }
}
