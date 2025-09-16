package com.bb.eodi.batch.job.legaldong.load.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * 법정동코드 조회 API 결과 row 데이터 모델
 */
public record LegalDongApiResponseRow (
        // 법정동 코드
        String region_cd,
        // 시/도 코드
        String sido_cd,
        // 시/군/구 코드
        String sgg_cd,
        // 동 코드
        String umd_cd,
        // 읍/면/리 코드
        String ri_cd,
        // 법정동 명
        String locatadd_nm,
        // 법정동 서열
        int locat_order,
        // 비고
        String locat_rm,
        // 상위지역코드
        String locathigh_cd
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
