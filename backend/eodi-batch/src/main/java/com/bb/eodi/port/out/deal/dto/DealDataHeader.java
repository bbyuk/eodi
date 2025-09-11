package com.bb.eodi.port.out.deal.dto;

/**
 * 부동산 실거래가 데이터 API 응답 헤더
 */
public record DealDataHeader(
        // 결과코드
        String resultCode,
        // 결과메세지
        String resultMsg
) {
}
