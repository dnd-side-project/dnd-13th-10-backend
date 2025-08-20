package com.seed.domain.mapping.like.service;

import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.mapping.like.entity.Like;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeQueryServiceTest {

    @Autowired
    private LikeQueryService likeQueryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemoirRepository memoirRepository;

    @Autowired
    private LikeRepository likeRepository;

    User user;
    Memoir memoir1, memoir2, memoir3, memoir4, memoir5;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .name("test")
                .socialId("1111")
                .role(Role.USER)
                .build();

        memoir1 = Memoir.builder()
                .companyName("삼성")
                .interviewMood(InterviewMood.QUIET)
                .position(Position.SERVICE)
                .type(MemoirType.QUICK)
                .build();

        memoir2 = Memoir.builder()
                .companyName("신한은행")
                .interviewMood(InterviewMood.QUIET)
                .position(Position.FINANCE_INSURANCE)
                .type(MemoirType.QUICK)
                .build();

        memoir3 = Memoir.builder()
                .companyName("롯데")
                .interviewMood(InterviewMood.QUIET)
                .position(Position.MARKETING_PR)
                .type(MemoirType.GENERAL)
                .build();

        memoir4 = Memoir.builder()
                .companyName("카카오")
                .interviewMood(InterviewMood.FRIENDLY)
                .position(Position.ADMIN_LEGAL)
                .type(MemoirType.QUICK)
                .build();

        memoir5 = Memoir.builder()
                .companyName("CJ")
                .interviewMood(InterviewMood.COMFORTABLE)
                .position(Position.ACCOUNTING_FINANCE)
                .type(MemoirType.GENERAL)
                .build();

        userRepository.save(user);
        memoirRepository.saveAll(List.of(memoir1, memoir2, memoir3, memoir4, memoir5));

        createLikes(user, memoir1);
        createLikes(user, memoir2);
        createLikes(user, memoir3);
        createLikes(user, memoir4);
        createLikes(user, memoir5);
    }


    @DisplayName("내가 좋아요를 누른 회고 글 목록 조회")
    @Test
    void getLikedMemoirs(){

        //given when
        List<CommonMemoirResponse> likedMemoirs = likeQueryService.getLikedMemoirs(user.getId());

        //then
        Assertions.assertThat(likedMemoirs).isNotEmpty();
        Assertions.assertThat(likedMemoirs.size()).isEqualTo(5);
    }

    private void createLikes(User user, Memoir memoir) {
        likeRepository.save(
                Like.builder()
                        .user(user)
                        .memoir(memoir)
                        .build()
        );
    }
}