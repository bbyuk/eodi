package com.bb.eodi.common.model;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ID기반 공통 커서 Wrapper
 * @param <T>
 */
@Slf4j
public record Cursor<T>(
        List<T> content,
        Long nextId,
        boolean hasNext,
        int pageSize
){

    public <R> Cursor<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mappedContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new Cursor<>(
                mappedContent,
                nextId,
                hasNext,
                pageSize
        );
    }

    public static <T> Cursor<T> from(List<T> content, int pageSize) {
        if (content.size() > pageSize) {
            try {
                T next = content.get(pageSize);
                Field id = next.getClass().getDeclaredField("id");
                return new Cursor<T>(content.subList(0, pageSize), id.getLong(next), true, pageSize);
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Cursor 인스턴스 생성중 오류가 발생했습니다.");
                throw new RuntimeException(e);
            }
        }
        else {
            return new Cursor<T>(content, null, false, pageSize);
        }
    }
}
