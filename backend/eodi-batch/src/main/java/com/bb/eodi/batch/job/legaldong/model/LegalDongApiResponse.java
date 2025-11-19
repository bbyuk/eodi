package com.bb.eodi.batch.job.legaldong.model;

import java.util.List;
import java.util.Map;

/**
 * 법정동코드 조회 API 응답
 */
public record LegalDongApiResponse(
        List<Map<String, Object>> StanReginCd,
        Map<String, Object> RESULT
) {

    /**
     * 성공시 StanReginCd가 루트 노드
     * 실패시 RESULT가 루트 노드
     * @return isSuccess
     */
    public boolean isSuccess() {
        return StanReginCd != null && RESULT == null;
    }
}
