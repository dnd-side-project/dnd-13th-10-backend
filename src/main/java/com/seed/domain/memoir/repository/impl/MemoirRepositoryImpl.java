package com.seed.domain.memoir.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.repository.MemoirQueryRepository;
import com.seed.domain.question.entity.QQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                        memoir.type.stringValue(), // Enum -> String
                        memoir.interviewStatus.stringValue(),
                        memoir.interviewMethod.stringValue(),
                        memoir.companyName,
                        memoir.position.stringValue(),
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
}