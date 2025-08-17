package com.seed.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.comment.converter.CommentConverter;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.comment.entity.QComment;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CursorPage<List<CommentResponse.InfoDTO>> getComments(Long memoirId, Long cursor, int size) {

        QComment comment = QComment.comment;

        JPAQuery<Comment> query;

        query = queryFactory.selectFrom(comment)
                .where(comment.memoir.id.eq(memoirId))
                .orderBy(comment.id.asc())
                .limit(size + 1);

        if(cursor != null) {
            query.where(comment.id.gt(cursor));
        }

        List<Comment> comments = query.fetch();

        boolean hasNext = false;
        Long nextCursor = null;

        if (comments.size() > size) {
            comments = comments.subList(0, comments.size() - 1);
            nextCursor = comments.get(comments.size() - 1).getId();
            hasNext = true;
        }

        List<CommentResponse.InfoDTO> commentDTOs = comments.stream().map(CommentConverter::toInfoDTO).toList();

        return CursorPage.of(size, nextCursor, hasNext, commentDTOs);
    }
}
