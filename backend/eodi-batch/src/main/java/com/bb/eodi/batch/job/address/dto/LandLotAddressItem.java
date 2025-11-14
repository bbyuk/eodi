package com.bb.eodi.batch.job.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 지번 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 건물DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Builder
@AllArgsConstructor
public class LandLotAddressItem {

    // 법정동코드
    private String legalDongCode;
    // 시도명
    private String sidoName;
    // 시군구명
    private String sigunguName;
    // 법정읍면동명
    private String legalUmdName;
    // 법정리명
    private String legalRiName;
    // 산여부
    private String isMountain;
    // 지번본번(번지)
    private String landLotMainNo;
    // 지번부번(호)
    private String landLotSubNo;
    // 지번일련번호
    private String landLotSeq;
    // 도로명코드
    private String roadNameCode;
    // 지하여부
    private String isUnderground;
    // 건물본번
    private String buildingMainNo;
    // 건물부번
    private String buildingSubNo;
    // 이동사유코드
    private String changeReasonCode;
}
