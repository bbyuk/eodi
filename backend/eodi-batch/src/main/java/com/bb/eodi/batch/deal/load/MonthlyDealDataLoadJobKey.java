package com.bb.eodi.batch.deal.load;

/**
 * 월별 부동산 거래 데이터 적재 배치 컨텍스트 key
 */
public enum MonthlyDealDataLoadJobKey {
    // 대상 지역
    REGION,
    // 거래년월 (6자리)
    DEAL_MONTH
}
