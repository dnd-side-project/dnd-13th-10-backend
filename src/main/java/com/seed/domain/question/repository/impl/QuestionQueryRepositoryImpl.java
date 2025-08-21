package com.seed.domain.question.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seed.domain.memoir.entity.QMemoir;
import com.seed.domain.memoir.enums.MemoirType;
import com.seed.domain.question.dto.request.QuestionSearchRequest;
import com.seed.domain.question.dto.response.QuestionResponse;
import com.seed.domain.question.entity.QQuestion;
import com.seed.domain.question.entity.Question;
import com.seed.domain.question.enums.QuestionType;
import com.seed.domain.question.repository.QuestionQueryRepository;
import com.seed.global.code.EnumCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestionResponse> searchQuestions(QuestionSearchRequest searchReq) {
        QQuestion question = QQuestion.question;
        QMemoir memoir = QMemoir.memoir;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(memoir.isUse.isTrue())
                .and(memoir.isPublic.isTrue())
                .and(memoir.isTmp.isFalse());

        if (searchReq.type() != null) {
            builder.and(question.questionType.eq(EnumCode.valueOfCode(QuestionType.class,searchReq.type())));
        }

        if (searchReq.memoirType() != null) {
            builder.and(memoir.type.eq(EnumCode.valueOfCode(MemoirType.class, searchReq.memoirType())));
        }

        if (searchReq.condition() != null) {
            builder.and(
                    question.title.containsIgnoreCase(searchReq.condition())
                            .or(question.answer.containsIgnoreCase(searchReq.condition()))
            );
        }

        List<Question> listQuestion = queryFactory.selectFrom(question)
                .join(memoir)
                .on(question.id.eq(memoir.id))
                .where(builder)
                .orderBy(question.createdAt.desc())
                .fetch();

        return QuestionResponse.fromEntityList(listQuestion);
    }
}
