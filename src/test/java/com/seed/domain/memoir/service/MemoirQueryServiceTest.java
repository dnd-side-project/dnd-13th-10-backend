package com.seed.domain.memoir.service;

import com.seed.domain.comment.entity.Comment;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.InterviewMood;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.enums.Role;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.paging.CursorPage;
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
class MemoirQueryServiceTest {

    @Autowired
    private MemoirQueryService memoirQueryService;

    @Autowired
    private MemoirRepository memoirRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    User user;
    Memoir memoir1,memoir2,memoir3, memoir4, memoir5;

    @BeforeEach
    void setUp() {
//        user = User.builder()
//                .name("test")
//                .socialId("1111")
//                .role(Role.USER)
//                .build();

        user = userRepository.findById(1L)
                .get();

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

        Memoir memoir6 = createMemoir("네이버", InterviewMood.PRESSURING, Position.DESIGN, MemoirType.GENERAL);
        Memoir memoir7 = createMemoir("현대", InterviewMood.QUIET, Position.MARKETING_PR, MemoirType.QUICK);
        Memoir memoir8 = createMemoir("기아", InterviewMood.PRESSURING, Position.HR, MemoirType.GENERAL);
        Memoir memoir9 = createMemoir("Meta", InterviewMood.FRIENDLY, Position.DEVELOPMENT_DATA, MemoirType.GENERAL);
        Memoir memoir10 = createMemoir("AWS", InterviewMood.PRESSURING, Position.DEVELOPMENT_DATA, MemoirType.QUICK);

        userRepository.save(user);
        memoirRepository.saveAll(List.of(memoir1, memoir2, memoir3, memoir4, memoir5, memoir6, memoir7, memoir8, memoir9, memoir10));

        Comment comment1 = createComment(memoir1, "댓글1");
        Comment comment2 = createComment(memoir3, "댓글2");
        Comment comment3 = createComment(memoir4, "댓글3");
        Comment comment4 = createComment(memoir5, "댓글4");
        Comment comment5 = createComment(memoir6, "댓글5");
        Comment comment6 = createComment(memoir7, "댓글6");
        Comment comment7 = createComment(memoir8, "댓글7");
        Comment comment8 = createComment(memoir9, "댓글8");

        commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8));
    }


    @DisplayName("")
    @Test
    @Rollback(false)
    void getCommetedMemoirs(){

//        //given
//        CursorPage<List<CommonMemoirResponse>> commetedMemoirs = memoirQueryService
//                .getCommetedMemoirs(user.getId(), null, 10);
//
//        //when
//
//        //then
//        Assertions.assertThat(commetedMemoirs.getData()).isNotNull();
//        Assertions.assertThat(commetedMemoirs.getData().size()).isEqualTo(8);
//        Assertions.assertThat(commetedMemoirs.isHasNext()).isFalse();
//        Assertions.assertThat(commetedMemoirs.getNextCursor()).isNull();
//        Assertions.assertThat(commetedMemoirs.getData().get(commetedMemoirs.getData().size() - 1).getId()).isEqualTo(1);
    }

    private Memoir createMemoir(String companyName, InterviewMood interviewMood, Position position, MemoirType type) {
        return Memoir.builder()
                .companyName(companyName)
                .interviewMood(interviewMood)
                .position(position)
                .type(type)
                .build();
    }

    private Comment createComment(Memoir memoir, String content) {
        return Comment.builder()
                .user(user)
                .content(content)
                .memoir(memoir)
                .build();
    }
}