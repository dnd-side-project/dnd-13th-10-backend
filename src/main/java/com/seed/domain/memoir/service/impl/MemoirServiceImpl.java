package com.seed.domain.memoir.service.impl;

import com.seed.domain.memoir.repository.MemoirRepository;
import com.seed.domain.memoir.service.MemoirService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;
}
