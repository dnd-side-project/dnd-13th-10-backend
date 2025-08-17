package com.seed.domain.mapping.bookmark.service;

import com.seed.domain.mapping.bookmark.entity.BookMark;
import com.seed.domain.mapping.bookmark.repository.BookMarkRepository;
import com.seed.domain.mapping.converter.CommonMemoirConverter;
import com.seed.domain.mapping.dto.response.CommonMemoirResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookMarkQueryService {

    private final BookMarkRepository bookMarkRepository;

    public List<CommonMemoirResponse> getBookMarkedMemoirs(Long userId) {
        List<BookMark> bookMarkedMemoirs = bookMarkRepository.findAllByUserId(userId);

        return bookMarkedMemoirs.stream().map(
                bookMark -> CommonMemoirConverter.fromEntity(bookMark.getMemoir())
        ).toList();
    }
}
