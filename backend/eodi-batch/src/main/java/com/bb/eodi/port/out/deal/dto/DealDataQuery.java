package com.bb.eodi.port.out.deal.dto;

/**
 * 부동산 실거래 데이터 요청 쿼리 파라미터
 */
public record DealDataQuery(
        // 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
        String legalDongCode,
        // 계약월, 실거래 자료의 계약년월(6자리)
        String dealDate
) {
}
