package com.seed.domain.comment.service;

import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.comment.repository.CommentRepository;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.enums.InterviewMood;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.schedule.enums.Position;
import com.seed.domain.user.entity.User;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.paging.CursorPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentQueryServiceTest {

    @Autowired
    private CommentQueryService commentQueryService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemoirRepository memoirRepository;

    User user;
    Memoir memoir;

    @BeforeEach
    void setUp() {

        commentRepository.deleteAllInBatch();

//        user = User.builder()
//                .name("test")
//                .imageUrl("imageUrl")
//                .build();



//        userRepository.save(user);
        user = userRepository.findById(8L).get();

        memoir = Memoir.builder()
                .companyName("카카오")
                .interviewMood(InterviewMood.FRIENDLY)
                .position(Position.ADMIN_LEGAL)
                .type(MemoirType.QUICK)
                .user(user)
                .build();
        memoirRepository.save(memoir);

        Comment comment1 = createComment("comment1");
        Comment comment2 = createComment("comment2");
        Comment comment3 = createComment("comment3");
        Comment comment4 = createComment("comment4");
        Comment comment5 = createComment("comment5");
        Comment comment6 = createComment("comment6");
        Comment comment7 = createComment("comment7");
        Comment comment8 = createComment("comment8");
        Comment comment9 = createComment("comment9");
        Comment comment10 = createComment("comment10");

        commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4,
                comment5, comment6, comment7, comment8, comment9, comment10));
    }


    @DisplayName("")
    @Test
    @Rollback(false)
    void getComments(){

        //given when
        CursorPage<List<CommentResponse.CommentInfoDTO>> comments = commentQueryService.getComments(memoir.getId(), null, 10);

        //then
        assertThat(comments).isNotNull();
        assertThat(comments.getNextCursor()).isNull();
        assertThat(comments.getPageSize()).isEqualTo(10);
        assertThat(comments.isHasNext()).isFalse();

    }


    @DisplayName("")
    @Test
    void getCommentsWithSize(){

        //given when
        CursorPage<List<CommentResponse.CommentInfoDTO>> comments = commentQueryService.getComments(memoir.getId(), null, 5);

        //then
        assertThat(comments).isNotNull();
        assertThat(comments.getNextCursor()).isNotNull();
        assertThat(comments.getPageSize()).isEqualTo(5);
        assertThat(comments.isHasNext()).isTrue();
        assertThat(comments.getResult().size()).isEqualTo(5);
    }


    @DisplayName("")
    @Test
    void getCommentsWithSizeAndCursor(){

        //given
        CursorPage<List<CommentResponse.CommentInfoDTO>> comments1 = commentQueryService.getComments(memoir.getId(), null, 5);

        String nextCursor = comments1.getNextCursor();

        //when
        CursorPage<List<CommentResponse.CommentInfoDTO>> comments = commentQueryService.getComments(memoir.getId(), nextCursor, 5);

        //then
        assertThat(comments).isNotNull();
        assertThat(comments.getNextCursor()).isNull();
        assertThat(comments.getPageSize()).isEqualTo(5);
        assertThat(comments.isHasNext()).isFalse();
        assertThat(comments.getResult().size()).isEqualTo(5);
    }

    private Comment createComment(String content) {
        return Comment.builder()
                .user(user)
                .memoir(memoir)
                .content(content)
                .build();
    }
}