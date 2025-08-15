package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.dto.request.QuickMemoirRequest;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.question.dto.request.QuestionCreateRequest;
import com.seed.domain.question.entity.Question;
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
    public List<MemoirResponse> listMemoir() {
        List<Memoir> listMemoir = memoirRepository.findAllByIsPublicTrueAndIsUseTrueOrderByCreatedAtDesc();
        return MemoirResponse.fromMemoirList(listMemoir);
    }
}
