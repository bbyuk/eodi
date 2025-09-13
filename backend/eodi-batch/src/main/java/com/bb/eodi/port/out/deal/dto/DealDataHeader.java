package com.bb.eodi.port.out.deal.dto;

import lombok.Data;

/**
 * 부동산 실거래가 데이터 API 응답 헤더
 */
@Data
public class DealDataHeader {
    // 결과코드
    private String resultCode;
    // 결과메세지
    private String resultMsg;
}
