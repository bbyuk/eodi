package com.bb.eodi.port.out.deal.dto;

import java.util.List;

/**
 * 부동산 실거래가 데이터 API 응답 바디
 */
public record DealDataBody <T> (
        List<T> items,
        // 전체 결과 수
        int totalCount,
        // 한 페이지 결과 수
        int numberOfRows,
        // 페이지 번호
        int pageNo
) {
}
