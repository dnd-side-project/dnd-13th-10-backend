package com.seed.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.comment.converter.CommentConverter;
import com.seed.domain.comment.dto.response.CommentResponse;
import com.seed.domain.comment.entity.Comment;
import com.seed.domain.comment.entity.QComment;
import com.seed.domain.user.entity.QUser;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CursorPage<List<CommentResponse.CommentInfoDTO>> getComments(Long memoirId, String cursor, int size) {

        QComment comment = QComment.comment;
        QUser user = QUser.user;

        JPAQuery<Comment> query;

        // 부모 댓글에 대해서만 페이지네이션을 적용
        query = queryFactory.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(
                        comment.memoir.id.eq(memoirId),
                        comment.parentId.isNull(),
                        user.isUse.isTrue()
                )
                .orderBy(comment.id.asc())
                .limit(size + 1);

        if(cursor != null) {
            query.where(comment.id.gt(Long.parseLong(cursor)));
        }

        List<Comment> comments = query.fetch();

        boolean hasNext = false;
        String nextCursor = null;

        if (comments.size() > size) {
            comments = comments.subList(0, comments.size() - 1);
            nextCursor = comments.get(comments.size() - 1).getId().toString();
            hasNext = true;
        }

        List<Long> parentIds = comments.stream().map(Comment::getId).toList();

        Map<Long, List<Comment>> replies = findByParentIds(parentIds).stream()
                .collect(Collectors.groupingBy(Comment::getParentId));

        return CursorPage.of(size, nextCursor, hasNext, CommentConverter.toCommentListDTO(comments, replies));
    }

    private List<Comment> findByParentIds(List<Long> parentIds) {

        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return queryFactory.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(
                        comment.parentId.in(parentIds),
                        comment.user.isUse.isTrue()
                )
                .orderBy(comment.parentId.asc(), comment.id.desc())
                .fetch();
    }
}
