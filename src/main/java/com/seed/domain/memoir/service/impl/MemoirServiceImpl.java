package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.dto.request.MemoirProcRequest;
import com.seed.domain.memoir.dto.request.SearchMemoirRequest;
import com.seed.domain.memoir.dto.response.HotMemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirListResponse;
import com.seed.domain.memoir.dto.response.MemoirResponse;
import com.seed.domain.memoir.dto.response.MineMemoirListResponse;
import com.seed.domain.memoir.entity.Memoir;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.service.MemoirService;
import com.seed.domain.question.dto.request.QuestionProcRequest;
import com.seed.domain.question.entity.Question;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import com.seed.global.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;

    @Override
    @Transactional
    public Long createMemoir(Long userId, MemoirProcRequest memoirProcRequest) {
        Memoir memoir = MemoirProcRequest.toEntity(userId, memoirProcRequest);

        if (memoirProcRequest.getQuestions() != null) {
            List<QuestionProcRequest> listQuestionDTO = memoirProcRequest.getQuestions();
            List<Question> listQuestion = QuestionProcRequest.toEntityList(listQuestionDTO);
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

    @Override
    public List<MineMemoirListResponse> findListMineMemoir(Long userId, SearchMemoirRequest searchType) {
        return memoirRepository.findListMineMemoir(userId, searchType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotMemoirListResponse> findWeeklyTop10() {
        Pair<LocalDateTime, LocalDateTime> range = DateUtil.weekRangeKst();
        return memoirRepository.findWeeklyTop10(range.getFirst(), range.getSecond());
    }

    @Override
    @Transactional
    public Long modifyMemoir(MemoirProcRequest req) {
        Memoir memoir = memoirRepository.findById(req.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "해당 회고 정보를 찾을 수 없습니다."));

        // 1) 본문 수정
        memoir.modifyMemoirFromQuick(req);

        List<Question> listQuestion = memoir.getQuestions();
        List<QuestionProcRequest> listQuestionRes = req.getQuestions();

        // 결과 리스트
        List<Question> deleteListQuestion = new ArrayList<>();               // 지울 기존 엔티티
        List<QuestionProcRequest> addListQuestion = new ArrayList<>();       // 새로 추가할 요청 DTO

        // order 기준으로 빠르게 찾도록 맵 구성
        Map<Integer, Question> existingByOrder = listQuestion.stream()
                .collect(Collectors.toMap(Question::getDisplayOrder, q -> q));

        // order 집합
        Set<Integer> incomingOrders = new HashSet<>();

        // 1) 들어온 것 기준으로: 있으면 수정, 없으면 추가 후보에 담기
        for (QuestionProcRequest qReq : listQuestionRes) {
            int order = qReq.getOrder();
            incomingOrders.add(order);

            Question found = existingByOrder.get(order);
            if (found != null) {
                // 값만 변경(UPDATE)
                found.modifyQuestion(qReq);
            } else {
                // 새로 추가
                addListQuestion.add(qReq);
            }
        }

        // 2) 기존 것 중 들어오지 않은 order는 삭제 후보에 담기
        for (Question q : listQuestion) {
            if (!incomingOrders.contains(q.getDisplayOrder())) {
                deleteListQuestion.add(q);
            }
        }

        memoir.removeQuestions(deleteListQuestion);
        memoir.addQuestions(QuestionProcRequest.toEntityList(addListQuestion));

        return memoir.getId();
    }

    @Override
    @Transactional
    public void deleteMemoir(Long id) {
        Memoir memoir = memoirRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "해당 회고 정보를 찾을 수 없습니다."));

        memoir.unUse();
    }
}
