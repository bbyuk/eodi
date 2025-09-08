package com.bb.eodi.batch.legaldong.load.api;

/**
 * 법정동 외부 API 클라이언트
 */
public interface LegalDongApiClient {

    /**
     * 대상 지역의 전체 법정동 수를 리턴
     * @return totalCount
     */
    int getTotalCount(String targetRegion);
}
