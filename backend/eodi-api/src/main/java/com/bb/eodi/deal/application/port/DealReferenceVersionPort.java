package com.bb.eodi.deal.application.port;

import java.time.LocalDate;
import java.util.List;

/**
 * 부동산 실거래가 - ops.ReferenceVersion 포트
 */
public interface DealReferenceVersionPort {

    /**
     * 대상 목록에서 최신 버전을 조회한다. 대상 목록 중 가장 오래된 버전이 유효한 최신 버전.
     * @param targetNames 조회 대상 목록
     * @return 유효한 최신 버전
     */
    LocalDate findLastUpdatedDate(List<String> targetNames);
}
