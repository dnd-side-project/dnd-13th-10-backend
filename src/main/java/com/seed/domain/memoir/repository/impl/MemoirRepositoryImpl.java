package com.seed.domain.memoir.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.memoir.repository.MemoirQueryRepository;
import com.seed.domain.question.entity.QQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoirRepositoryImpl implements MemoirQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemoirListResponse> findListMemoir() {
        QMemoir memoir = QMemoir.memoir;
        QQuestion qQuestion = QQuestion.question; // 생성된 Q 클래스의 static 필드명 확인!

        return queryFactory
                .select(Projections.constructor(
                        MemoirListResponse.class,
                        memoir.id,
                        memoir.type, // Enum -> String
                        memoir.interviewStatus,
                        memoir.companyName,
                        memoir.position,
                        memoir.createdAt,
                        qQuestion.content // displayOrder = 1 인 첫 질문 (없으면 null)
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