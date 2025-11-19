package com.bb.eodi.batch.deal.eunms;

/**
 * 월별 부동산 거래 데이터 적재 배치 컨텍스트 key
 */
public enum MonthlyDealDataLoadJobKey {
    // 대상 지역
    REGION,
    // 거래년월 (6자리)
    DEAL_MONTH,
    // 부동산 거래 데이터 API 응답 임시 파일
    TEMP_FILE,
    // 재시작 로직 처리를 위한 키
    CURRENT_INDEX
}
