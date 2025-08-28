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
import com.seed.global.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public CursorPage<List<QuestionResponse>> searchQuestions(Long userId, QuestionSearchRequest searchReq, String cursor, int size) {
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

        if (searchReq.isMine()) {
            builder.and(memoir.user.id.eq(userId));
        }

        if(cursor != null) {
            Long cursorCondition = Long.parseLong(cursor);
            builder.and(question.id.lt(cursorCondition));
        }

        List<Question> listQuestion = queryFactory.selectFrom(question)
                .join(memoir)
                .on(question.memoir.id.eq(memoir.id))
                .where(builder)
                .orderBy(question.id.desc())
                .limit(size + 1)
                .fetch();

        List<QuestionResponse> listQuestionRes = QuestionResponse.fromEntityList(listQuestion);

        boolean hasNext = listQuestionRes.size() > size;
        if (hasNext) listQuestionRes = listQuestionRes.subList(0, size);

        String nextCursor = null;
        if (hasNext && !listQuestionRes.isEmpty()) {
            nextCursor = String.valueOf(listQuestionRes.get(listQuestionRes.size() - 1).getId());
        }

        return CursorPage.of(size, nextCursor, hasNext, listQuestionRes);
    }
}
