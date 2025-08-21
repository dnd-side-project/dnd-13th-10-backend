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

    private int pageSize;
    private String nextCursor;
    private boolean hasNext;
    private T result;

    public static <T> CursorPage<T> of(int pageNum, String nextCursor, boolean hasNext, T data) {
        return CursorPage.<T>builder()
                .pageSize(pageNum)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .result(data)
                .build();
    }
}
