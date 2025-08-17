package com.seed.domain.mapping.like.service;

import com.seed.domain.mapping.like.repository.LikeRepository;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.InterviewMood;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.enums.Role;
import com.seed.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeCommandServiceTest {

    @Autowired
    private LikeCommandService likeCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemoirRepository memoirRepository;

    User user;
    Memoir memoir;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .name("test")
                .socialId("1111")
                .role(Role.USER)
                .build();

        memoir = Memoir.builder()
                .interviewMood(InterviewMood.QUIET)
                .build();

        userRepository.save(user);
        memoirRepository.save(memoir);
    }


    @DisplayName("좋아요를 추가합니다.")
    @Test
    void likesUp(){

        //given when
        boolean isLiked = likeCommandService.toggleLike(user.getId(), memoir.getId());

        //then
        Assertions.assertThat(isLiked).isTrue();
    }


    @DisplayName("좋아요를 삭제합니다.")
    @Test
    void likesDown(){

        //given
        likeCommandService.toggleLike(user.getId(), memoir.getId());

        //when
        boolean isLiked = likeCommandService.toggleLike(user.getId(), memoir.getId());

        //then
        Assertions.assertThat(isLiked).isFalse();
    }


    @DisplayName("")
    @Test
    @Rollback(false)
    void insertMemoir(){

        //given
        Memoir memoir3 = Memoir.builder()
                .companyName("카카오")
                .interviewMood(InterviewMood.FRIENDLY)
                .position(Position.ADMIN_LEGAL)
                .type(MemoirType.QUICK)
                .build();

        memoirRepository.save(memoir3);

        //when

        //then
    }
}