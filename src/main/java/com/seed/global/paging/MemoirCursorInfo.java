package com.seed.global.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoirCursorInfo {
    private Long actionId;  // comment.id, like.id, bookmark.id
    private Long memoirId;  // memoir.id
}