package com.seed.domain.memoir.controller;

import com.seed.domain.memoir.service.MemoirService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("/memoir")
@RequiredArgsConstructor
public class MemoirController {

    private final MemoirService memoirService;
}
