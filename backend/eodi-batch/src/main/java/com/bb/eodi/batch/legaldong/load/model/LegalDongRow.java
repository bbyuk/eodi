package com.bb.eodi.batch.legaldong.load.model;

import lombok.Builder;
import lombok.Data;

/**
 * 법정동 코드 소스 데이터 row 모델
 */
@Data
@Builder
public class LegalDongRow {
    // 법정동코드
    private String legalDongCode;

    // 법정동명
    private String legalDongName;

    // 폐지여부
    private String closeYn;
}
