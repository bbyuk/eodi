package com.bb.eodi.legaldong.job.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 법정동 source data item
 */
@Data
@Builder
public class LegalDongItem {

    // 법정동코드
    private String legalDongCode;

    // 시도명
    private String sidoName;

    // 시군구명
    private String sigunguName;

    // 읍면동명
    private String umdName;

    // 리명
    private String riName;

    // 법정동 순위
    private String legalDongOrder;
    
    // 생성일자
    private String entranceDate;
    
    // 말소일자
    private String revocationDate;
    
    // 이전법정동코드
    private String beforeLegalDongCode;
}
