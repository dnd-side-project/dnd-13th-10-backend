package com.seed.global.oauth2.service;

import com.seed.domain.user.entity.User;
import com.seed.domain.user.enums.Role;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.oauth2.CustomOAuth2User;
import com.seed.global.oauth2.kakao.dto.KakaoUserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("카카오 로그인 시작");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("카카오 사용자 정보: {}", oAuth2User.getAttributes());

        String socialId = oAuth2User.getName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("socialId: {}", socialId);

        KakaoUserInfoDTO kakaoUserInfo = new KakaoUserInfoDTO(attributes);

        User user = saveOrUpdate(kakaoUserInfo);

        return new CustomOAuth2User(user, attributes);
    }

    private User saveOrUpdate(KakaoUserInfoDTO kakaoUserInfoDTO) {
        Optional<User> existingUser = userRepository.findBySocialId(kakaoUserInfoDTO.getId());

        // 이미 존재하는 유저라면 -> 기존 정보 업데이트 & 로그인
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.updateName(kakaoUserInfoDTO.getNickname());
            user.updateImageUrl(kakaoUserInfoDTO.getProfileImageUrl());
            return userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .socialId(kakaoUserInfoDTO.getId())
                    .name(kakaoUserInfoDTO.getNickname())
                    .role(Role.USER)
                    .imageUrl(kakaoUserInfoDTO.getProfileImageUrl())
                    .isUse(true)
                    .build();

            return userRepository.save(newUser);
        }

    }
}
