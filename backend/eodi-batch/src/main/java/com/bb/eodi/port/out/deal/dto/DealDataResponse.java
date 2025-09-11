package com.bb.eodi.port.out.deal.dto;

/**
 * 부동산 실거래가 데이터 API 응답 DTO
 */
public record DealDataResponse<T> (
        DealDataHeader header,
        DealDataBody<T> body
) {
}
