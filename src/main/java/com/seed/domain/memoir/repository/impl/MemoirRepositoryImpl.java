package com.seed.domain.memoir.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.comment.entity.QComment;
import com.seed.domain.mapping.converter.CommonMemoirConverter;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.*;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.entity.QMemoirViewHist;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirQueryRepository;
import com.seed.domain.memoir.repository.dto.MemoirCursor;
import com.seed.domain.memoir.repository.dto.MemoirDTO;
import com.seed.domain.memoir.repository.dto.QMemoirDTO;
import com.seed.domain.question.entity.QQuestion;
import com.seed.domain.user.entity.QUser;
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.seed.domain.comment.entity.QComment.comment;
import static com.seed.domain.mapping.bookmark.entity.QBookMark.bookMark;
import static com.seed.domain.mapping.like.entity.QLike.like;
import static com.seed.domain.memoir.entity.QMemoir.memoir;

@Repository
@RequiredArgsConstructor
public class MemoirRepositoryImpl implements MemoirQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemoirListResponse> findListMemoir() {
        QMemoir memoir = QMemoir.memoir;
        QQuestion qQuestion = QQuestion.question;

        return queryFactory
                .select(new QMemoirListResponse(
                        memoir.id,
                        memoir.type,
                        memoir.interviewStatus,
                        memoir.companyName,
                        memoir.position,
                        memoir.createdAt,
                        qQuestion.title        // displayOrder=1 첫 질문(없으면 null)
                ))
                .from(memoir)
                .leftJoin(qQuestion)
                .on(qQuestion.memoir.eq(memoir)
                        .and(qQuestion.displayOrder.eq(1)))
                .where(
                        memoir.isPublic.isTrue(),
                        memoir.isUse.isTrue(),
                        memoir.isTmp.isFalse()
                )
                .orderBy(memoir.createdAt.desc())
                .fetch();
    }

    @Override
    public List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchReq) {
        QMemoir m = memoir;

        // searchType 해석
        BooleanExpression dynamicCondition = getBooleanExpression(searchReq);

        // 엔티티로 가져와서 DTO 매핑 (DTO가 builder-only 이므로)
        List<Memoir> listMemoir = queryFactory
                .selectFrom(m)
                .where(
                        m.user.id.eq(userId),
                        m.isUse.isTrue(),
                        dynamicCondition
                )
                .orderBy(m.createdAt.desc(), m.id.desc())
                .fetch();

        return listMemoir.stream()
                .map(MineMemoirListResponse::fromEntity)
                .toList();
    }

    @Override
    public List<HotMemoirListResponse> findWeeklyTop10(LocalDateTime utcStart, LocalDateTime utcEnd) {
        QMemoir m = memoir;
        QMemoirViewHist v = QMemoirViewHist.memoirViewHist;
        QUser u = QUser.user;
        QQuestion q = QQuestion.question;

        // COALESCE(COUNT(v.id), 0) 표현 (QueryDSL)
        NumberExpression<Long> weeklyViews =
                com.querydsl.core.types.dsl.Expressions.numberTemplate(
                        Long.class,
                        "coalesce(count({0}), 0)",
                        v.id
                );

        return queryFactory
                .select(new QHotMemoirListResponse(
                        m.id,
                        m.type,               // MemoirType enum (DTO에서 description 변환)
                        u.name,               // String userName
                        m.companyName,        // String companyName
                        m.position,           // Position enum (DTO에서 description 변환)
                        q.title,              // String firstQuestion (displayOrder=1)
                        u.imageUrl,           // String imageUrl
                        m.createdAt,          // LocalDateTime createdAt
                        weeklyViews,          // Long weeklyViewCount (COALESCE 처리)
                        m.viewCount           // Integer totalViewCount (누적)
                ))
                .from(m)
                .join(m.user, u)
                .leftJoin(m.questions, q)
                .on(q.displayOrder.eq(1).and(q.isUse.isTrue()))
                .leftJoin(v) // ← LEFT JOIN 으로 ‘조회 없으면 0’ 유지
                .on(v.memoir.eq(m)
                        .and(v.viewedAt.goe(utcStart))
                        .and(v.viewedAt.lt(utcEnd)))
                .where(
                        m.isUse.isTrue(),
                        m.isPublic.isTrue(),
                        m.isTmp.isFalse()
                )
                .groupBy(
                        m.id, m.type, m.position, m.companyName,
                        u.name, u.imageUrl, m.createdAt, q.title, m.viewCount
                )
                .orderBy(weeklyViews.desc(), m.id.desc())
                .limit(10)
                .fetch();
    }

    public CursorPage<List<CommonMemoirResponse>> getLikedMemoirs(Long userId, String cursor, int size) {

        BooleanExpression condition = like.user.id.eq(userId)
                .and(memoir.isTmp.isFalse())
                .and(memoir.isUse.isTrue());

        if(cursor != null) {

            Long cursorCondition = Long.parseLong(cursor);

            condition = condition.and(like.id.lt(cursorCondition));
        }

        List<MemoirDTO> memoirDTOs = queryFactory.select(new QMemoirDTO(
                        memoir.id,
                        memoir.type,
                        memoir.companyName,
                        memoir.position,
                        memoir.createdAt,
                        like.id
                ))
                .from(like)
                .innerJoin(like.memoir, memoir)
                .where(condition)
                .orderBy(like.id.desc())
                .limit(size + 1)
                .fetch();

        return getListCursorPage(size, memoirDTOs);
    }

    public CursorPage<List<CommonMemoirResponse>> getBookMarkedMemoirs(Long userId, String cursor, int size) {
        BooleanExpression condition = bookMark.user.id.eq(userId)
                .and(memoir.isTmp.isFalse())
                .and(memoir.isUse.isTrue());


        if(cursor != null) {

            Long cursorCondition = Long.parseLong(cursor);

            condition = condition.and(bookMark.id.lt(cursorCondition));
        }

        List<MemoirDTO> memoirDTOS = queryFactory.select(new QMemoirDTO(
                        memoir.id,
                        memoir.type,
                        memoir.companyName,
                        memoir.position,
                        memoir.createdAt,
                        bookMark.id
                ))
                .from(bookMark)
                .join(bookMark.memoir, memoir)
                .where(condition)
                .orderBy(bookMark.id.desc())
                .limit(size + 1)
                .fetch();

        return getListCursorPage(size, memoirDTOS);
    }

    public CursorPage<List<CommonMemoirResponse>> getCommentedMemoirs(Long userId, String cursor, int size) {


        Long memoirId = null;
        Long commentId = null;

        if(cursor != null) {
            memoirId = Long.parseLong(cursor.split(":")[0]);
            commentId = Long.parseLong(cursor.split(":")[1]);
        }

        List<Tuple> rows = fetchMemoirId(userId, memoirId, commentId, size);

        boolean hasNext = rows.size() > size;

        if (hasNext) {
            rows = rows.subList(0, size);
        }

        List<Long> memoirIds = new ArrayList<>(rows.size());
        List<String> cursors = new ArrayList<>(rows.size());

        for (Tuple row : rows) {
            Long memoirIdFromRow = row.get(0, Long.class);
            Long commentIdFromRow = row.get(1, Long.class);
            memoirIds.add(memoirIdFromRow);

            String memoirStringId = memoirIdFromRow.toString();
            String commentStringId = commentIdFromRow.toString();

            cursors.add(memoirStringId + ":" + commentStringId);
        }

        List<Memoir> memoirs = queryFactory.selectFrom(memoir)
                .where(memoir.id.in(memoirIds))
                .fetch();

        Map<Long, Memoir> idToMemoirs
                = memoirs.stream().collect(Collectors.toMap(Memoir::getId, Function.identity()));

        List<Memoir> ordered = new ArrayList<>(memoirIds.size());

        for (Long id : memoirIds) {
            Memoir memoir = idToMemoirs.get(id);
            if (memoir != null) ordered.add(memoir);
        }

        List<CommonMemoirResponse> responses = ordered.stream().map(CommonMemoirConverter::fromEntity).toList();

        String nextCursor = hasNext ? cursors.get(cursors.size() - 1) : null;

        return CursorPage.of(size, nextCursor, hasNext, responses);
    }

    private List<Tuple> fetchMemoirId(
            Long userId,
            Long memoirId,
            Long commentId,
            int size
    ) {

        BooleanBuilder havingCursor = new BooleanBuilder();

        if (memoirId != null && commentId != null) {
            havingCursor.and(
                    comment.id.max().lt(memoirId)
                            .or(
                                    comment.memoir.id.max().eq(commentId)
                                            .and(comment.memoir.id.lt(memoirId))
                            )
            );
        }

        return queryFactory.select(comment.memoir.id, comment.id.max())
                .from(comment)
                .where(comment.user.id.eq(userId))
                .groupBy(comment.memoir.id)
                .having(havingCursor)
                .orderBy(comment.id.max().desc(), comment.memoir.id.desc())
                .limit(size + 1)
                .fetch();

    }

    private CursorPage<List<CommonMemoirResponse>> getListCursorPage(int size, List<MemoirDTO> memoirDTOs) {
        boolean hasNext = false;
        String nextCursor = null;

        if (memoirDTOs.size() > size) {
            hasNext = true;
            nextCursor = memoirDTOs.get(size - 1).getActionId().toString(); // ✅ 마지막 항목의 ID 사용
            memoirDTOs = memoirDTOs.subList(0, size);
        }

        List<CommonMemoirResponse> responses = memoirDTOs.stream()
                .map(CommonMemoirConverter::fromDto)
                .toList();

        return CursorPage.of(size, nextCursor, hasNext, responses);
    }



    private static BooleanExpression getBooleanExpression(
            SearchMemoirRequest request
    ) {
        if (!StringUtils.hasText(request.searchType())) {
            return memoir.isTmp.isFalse();
        }

        return switch (request.searchType()) {
            case "quick"   -> memoir.type.eq(MemoirType.QUICK).and(memoir.isTmp.isFalse());
            case "general" -> memoir.type.eq(MemoirType.GENERAL).and(memoir.isTmp.isFalse());
            default -> memoir.isTmp.isFalse();
        };
    }
}