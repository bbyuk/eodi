package com.bb.eodi.common.presentation.response;

import com.bb.eodi.common.model.Cursor;

import java.util.List;

/**
 * ID 기반 공통 커서 response
 * @param content
 * @param <T>
 */
public record CursorResponse<T>(
        List<T> content,
        Long nextId,
        boolean hasNext
) {
    public static <T> CursorResponse<T> from(Cursor<T> cursor) {
        return new CursorResponse<>(cursor.content(), cursor.nextId(), cursor.hasNext());
    }
}
