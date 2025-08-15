package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.question.dto.request.QuestionCreateRequest;
import com.seed.domain.question.entity.Question;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;

    @Override
    @Transactional
    public Long createQuickMemoir(QuickMemoirRequest quickMemoirRequest) {
        Memoir memoir = QuickMemoirRequest.toEntity(quickMemoirRequest);

        if (quickMemoirRequest.getQuestions() != null) {
            List<QuestionCreateRequest> listQuestionDTO = quickMemoirRequest.getQuestions();
            List<Question> listQuestion = QuestionCreateRequest.toEntityList(listQuestionDTO);
            memoir.addQuestions(listQuestion);
        }

        Memoir save = memoirRepository.save(memoir);
        return save.getId();
    }

    @Override
    public List<MemoirListResponse> findListMemoir() {
        return memoirRepository.findListMemoir();
    }

    @Override
    public MemoirResponse findMemoirById(Long id) {
        return memoirRepository.findById(id)
                .map(MemoirResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "해당 회고 정보를 찾을 수 없습니다."));
    }
}
