package com.bb.eodi.batch.legaldong.port;

import com.bb.eodi.batch.legaldong.model.LegalDongApiResponseRow;

import java.util.List;

/**
 * 법정동 외부 API 클라이언트
 */
public interface LegalDongDataPort {

    /**
     * 대상 지역의 전체 법정동 수를 리턴
     * @return totalCount
     */
    int getTotalCount(String targetRegion);

    /**
     * 대상 지역의 법정동 API 응답 body 데이터를 리턴
     * @param targetRegion
     * @return responseBody
     */
    List<LegalDongApiResponseRow> findByRegion(String targetRegion, int pageNum);

    /**
     * API page size 리턴
     * @return pageSize
     */
    int getPageSize();
}
