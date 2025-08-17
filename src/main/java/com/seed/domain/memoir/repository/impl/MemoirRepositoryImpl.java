package com.seed.domain.memoir.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.*;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.entity.QMemoirView;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirQueryRepository;
import com.seed.domain.question.entity.QQuestion;
import com.seed.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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
                        qQuestion.content        // displayOrder=1 첫 질문(없으면 null)
                ))
                .from(memoir)
                .leftJoin(qQuestion)
                .on(qQuestion.memoir.eq(memoir)
                        .and(qQuestion.displayOrder.eq(1)))
                .where(
                        memoir.isPublic.isTrue(),
                        memoir.isUse.isTrue()
                )
                .orderBy(memoir.createdAt.desc())
                .fetch();
    }

    @Override
    public List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchReq) {
        QMemoir m = QMemoir.memoir;

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
        QMemoir m = QMemoir.memoir;
        QMemoirView v = QMemoirView.memoirView;
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
                        q.content,            // String firstQuestion (displayOrder=1)
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
                        u.name, u.imageUrl, m.createdAt, q.content, m.viewCount
                )
                .orderBy(weeklyViews.desc(), m.id.desc())
                .limit(10)
                .fetch();
    }

    private static BooleanExpression getBooleanExpression(
            SearchMemoirRequest request
    ) {
        if (!StringUtils.hasText(request.searchType())) {
            return null;
        }

        return switch (request.searchType()) {
            case "quick"   -> QMemoir.memoir.type.eq(MemoirType.QUICK).and(QMemoir.memoir.isTmp.isFalse());
            case "general" -> QMemoir.memoir.type.eq(MemoirType.GENERAL).and(QMemoir.memoir.isTmp.isFalse());
            case "tmp" -> QMemoir.memoir.isTmp.isTrue();
            default -> null;
        };
    }
}