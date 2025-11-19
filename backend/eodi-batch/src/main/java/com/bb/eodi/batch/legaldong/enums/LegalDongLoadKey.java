package com.bb.eodi.batch.legaldong.enums;

/**
 * 법정동 적재 배치 key
 */
public enum LegalDongLoadKey {
    // 전체 처리 데이터 수
    TOTAL_COUNT,
    // 현재 페이지
    PAGE_NUM,
    // 처리된 데이터 수
    PROCESSED_COUNT,
    // 읽어야할 데이터
    DATA_FILE,
    // read start offset
    READ_START_OFFSET,
    // page size
    PAGE_SIZE
}
