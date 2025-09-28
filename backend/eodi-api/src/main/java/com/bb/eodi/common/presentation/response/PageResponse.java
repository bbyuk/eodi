package com.bb.eodi.common.presentation.response;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 공통 PageResponse
 * @param content 데이터 내용
 * @param page 현재 페이지
 * @param size 페이지 크기
 * @param totalElements 전체 element 수
 * @param totalPages 전체 페이지 수
 * @param first 첫 페이지 여부
 * @param last 마지막 페이지 여부
 * @param <T> 데이터 element 타입
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
