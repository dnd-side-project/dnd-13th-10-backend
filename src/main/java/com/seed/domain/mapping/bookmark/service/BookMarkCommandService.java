package com.seed.domain.mapping.bookmark.service;

import com.seed.domain.mapping.bookmark.converter.BookMarkConverter;
import com.seed.domain.mapping.bookmark.entity.BookMark;
import com.seed.domain.mapping.bookmark.repository.BookMarkRepository;
import com.seed.domain.mapping.like.converter.LikeConverter;
import com.seed.domain.mapping.like.entity.Like;
import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.user.repository.UserRepository;
import com.seed.global.exception.BusinessException;
import com.seed.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookMarkCommandService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;
    private final MemoirRepository memoirRepository;

    public boolean toggleBookMark(Long userId, Long memoirId) {

        boolean isBookMarked;

        Optional<BookMark> bookMark = bookMarkRepository.findByUserIdAndMemoirId(userId, memoirId);

        // 이미 북마크를 누른 상태라면 -> 취소
        if (bookMark.isPresent()) {
            bookMarkRepository.delete(bookMark.get());
            isBookMarked = false;
        }
        // 북마크를 누르지 않은 상태라면 -> 추가
        else {
            bookMarkUp(userId, memoirId);
            isBookMarked = true;
        }

        return isBookMarked;
    }

    private void bookMarkUp(Long userId, Long memoirId) {
        if(!userRepository.existsById(userId)){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"해당 회원 정보를 찾을 수 없습니다.");
        }

        if(!memoirRepository.existsById(memoirId)){
            throw new BusinessException(ErrorCode.NOT_FOUND, "해당 회고 정보를 찾을 수 없습니다.");
        }

        bookMarkRepository.save(BookMarkConverter.toBookMark(userId, memoirId));
    }
}
