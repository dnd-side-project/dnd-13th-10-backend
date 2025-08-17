package com.seed.global.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursorPage<T> {

    private int pageNum;
    private Long nextCursor;
    private boolean hasNext;
    private T data;

    public static <T> CursorPage<T> of(int pageNum, Long nextCursor, boolean hasNext, T data) {
        return CursorPage.<T>builder()
                .pageNum(pageNum)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .data(data)
                .build();
    }
}
